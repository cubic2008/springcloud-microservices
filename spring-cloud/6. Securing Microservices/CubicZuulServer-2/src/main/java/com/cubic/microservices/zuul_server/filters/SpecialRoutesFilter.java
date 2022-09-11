package com.cubic.microservices.zuul_server.filters;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.cubic.microservices.zuul_server.domain.AbTestingRoute;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

//  This route filter will be stopped for this version (v07)
//@Component
public class SpecialRoutesFilter extends ZuulFilter {

	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

	@Autowired
	FilterUtils filterUtils;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	@Override
	public Object run() throws ZuulException {
		System.out.println("SpecialRoutesFilter.run() is invoked.");
		RequestContext ctx = RequestContext.getCurrentContext();
		System.out.println("filterUtils.getServiceId() = " + filterUtils.getServiceId());
		AbTestingRoute abTestRoute = getAbRoutingInfo(filterUtils.getServiceId());
		if (abTestRoute != null && useSpecialRoute(abTestRoute)) {
			System.out.println("useSpecialRoute: abTestRoute = " + abTestRoute);
//			String route = buildRouteString(ctx.getRequest().getRequestURI(), abTestRoute.getEndpoint(),
//					abTestRoute.getZuulServiceRoute());
//			forwardToSpecialRoute(route);
			System.out.println( "abTestRoute.getEndpoint() = " + abTestRoute.getEndpoint() );
			try {
				ctx.setRouteHost( new URL ( abTestRoute.getEndpoint() ) );
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(
					"useSpecialRoute is not used: abTestRoute = " + (abTestRoute == null ? "null" : abTestRoute));
		}
		return null;
	}

	private AbTestingRoute getAbRoutingInfo(String serviceName) {
		ResponseEntity<AbTestingRoute> restExchange = null;
		try {
			restExchange = restTemplate.exchange("http://special-route-service/v1/route/abtesting/{serviceName}",
					HttpMethod.GET, null, AbTestingRoute.class, serviceName);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				return null;
			}
			throw ex;
		}
		return restExchange.getBody();
	}

	public boolean useSpecialRoute(AbTestingRoute testRoute) {
		Random random = new Random();
		if (testRoute.getActive().equals("N")) {
			return false;
		}
		int value = random.nextInt((10 - 1) + 1) + 1;
		if (testRoute.getWeight() < value) {
			return true;
		}
		return false;
	}

//	private String buildRouteString(String oldEndpoint, String newEndpoint, String serviceName) {
//		int index = oldEndpoint.indexOf(serviceName);
//
//		System.out.println("oldEndpoint = " + oldEndpoint + ", newEndpoint = " + newEndpoint + ", serviceName = "
//				+ serviceName + ", index = " + index);
//
//		String strippedRoute = oldEndpoint.substring(index + serviceName.length());
//		String routeString = String.format("%s%s", newEndpoint, strippedRoute);
//		System.out.println("Target route: " + routeString);
//		return routeString;
//	}

//	@Autowired
//	private ProxyRequestHelper helper;
//
//	private void forwardToSpecialRoute(String route) {
//		RequestContext context = RequestContext.getCurrentContext();
//		HttpServletRequest request = context.getRequest();
//
//		MultiValueMap<String, String> headers = this.helper.buildZuulRequestHeaders(request);
//		MultiValueMap<String, String> params = this.helper.buildZuulRequestQueryParams(request);
//		String verb = getVerb(request);
//		InputStream requestEntity = getRequestBody(request);
//		if (request.getContentLength() < 0) {
//			context.setChunkedRequestBody();
//		}
//
//		this.helper.addIgnoredHeaders();
//		CloseableHttpClient httpClient = null;
//		HttpResponse response = null;
//
//		try {
//			httpClient = HttpClients.createDefault();
//			response = forward(httpClient, verb, route, request, headers, params, requestEntity);
//			setResponse(response);
////			context.setRouteHost(null);
//			context.setRouteHost( new URL ( "http://localhost:2021" ) );
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//				httpClient.close();
//			} catch (IOException ex) { }
//		}
//	}
//
//	private String getVerb(HttpServletRequest request) {
//		String sMethod = request.getMethod();
//		return sMethod.toUpperCase();
//	}
//
//	private void setResponse(HttpResponse response) throws IOException {
//		try {
//			this.helper.setResponse(response.getStatusLine().getStatusCode(),
//					response.getEntity() == null ? null : response.getEntity().getContent(),
//					revertHeaders(response.getAllHeaders()));
//			InputStream respContentIS = response.getEntity() == null ? null : response.getEntity().getContent();
//			printRespContent(respContentIS);
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//			throw ioe;
//		}
//	}
//
//	private void printRespContent(InputStream is) {
//		byte[] bb = new byte[1024];
//		StringBuilder sb = new StringBuilder("");
//		try {
//			int len = is.read(bb);
//			while (len != -1) {
//				sb.append(new String(bb));
//				len = is.read(bb);
//			}
//			System.out.println("content = " + sb.toString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private MultiValueMap<String, String> revertHeaders(Header[] headers) {
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//		for (Header header : headers) {
//			String name = header.getName();
//			if (!map.containsKey(name)) {
//				map.put(name, new ArrayList<String>());
//			}
//			map.get(name).add(header.getValue());
//		}
//		return map;
//	}
//
//	private HttpResponse forward(HttpClient httpclient, String verb, String uri, HttpServletRequest request,
//			MultiValueMap<String, String> headers, MultiValueMap<String, String> params, InputStream requestEntity)
//			throws Exception {
//		Map<String, Object> info = this.helper.debug(verb, uri, headers, params, requestEntity);
//		URL host = new URL(uri);
//		HttpHost httpHost = getHttpHost(host);
//
//		HttpRequest httpRequest;
//		int contentLength = request.getContentLength();
//		InputStreamEntity entity = new InputStreamEntity(requestEntity, contentLength,
//				request.getContentType() != null ? ContentType.create(request.getContentType()) : null);
//		switch (verb.toUpperCase()) {
//		case "POST":
//			HttpPost httpPost = new HttpPost(uri);
//			httpRequest = httpPost;
//			httpPost.setEntity(entity);
//			break;
//		case "PUT":
//			HttpPut httpPut = new HttpPut(uri);
//			httpRequest = httpPut;
//			httpPut.setEntity(entity);
//			break;
//		case "PATCH":
//			HttpPatch httpPatch = new HttpPatch(uri);
//			httpRequest = httpPatch;
//			httpPatch.setEntity(entity);
//			break;
//		default:
//			httpRequest = new BasicHttpRequest(verb, uri);
//
//		}
//		try {
//			httpRequest.setHeaders(convertHeaders(headers));
//			HttpResponse zuulResponse = forwardRequest(httpclient, httpHost, httpRequest);
//
//			return zuulResponse;
//		} finally {
//		}
//	}
//
//	private HttpHost getHttpHost(URL host) {
//		HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(), host.getProtocol());
//		return httpHost;
//	}
//
//	private Header[] convertHeaders(MultiValueMap<String, String> headers) {
//		List<Header> list = new ArrayList<>();
//		for (String name : headers.keySet()) {
//			for (String value : headers.get(name)) {
//				list.add(new BasicHeader(name, value));
//			}
//		}
//		return list.toArray(new BasicHeader[0]);
//	}
//
//	private HttpResponse forwardRequest(HttpClient httpclient, HttpHost httpHost, HttpRequest httpRequest)
//			throws IOException {
//		return httpclient.execute(httpHost, httpRequest);
//	}
//
//	private InputStream getRequestBody(HttpServletRequest request) {
//		InputStream requestEntity = null;
//		try {
//			requestEntity = request.getInputStream();
//		} catch (IOException ex) {
//			// no requestBody is ok.
//		}
//		return requestEntity;
//	}

	@Override
	public String filterType() {
		return FilterUtils.ROUTE_FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

}
