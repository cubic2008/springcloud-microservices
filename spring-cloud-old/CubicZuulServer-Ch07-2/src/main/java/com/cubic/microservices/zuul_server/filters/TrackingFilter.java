package com.cubic.microservices.zuul_server.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cubic.microservices.zuul_server.config.ServiceConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TrackingFilter extends ZuulFilter {

	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

	@Autowired
	FilterUtils filterUtils;

	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	@Override
	public Object run() throws ZuulException {
		System.out.println("TrackingFilter.run() is invoked.");
		if (isCorrelationIdPresent()) {
			logger.debug("tmx-correlation-id found in tracking filter: {}.", filterUtils.getCorrelationId());
		} else {
			filterUtils.setCorrelationId(generateCorrelationId());
			logger.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
		}

		logger.debug ( "The organization name from the token is : " + getOrganizationName() );
		
		RequestContext ctx = RequestContext.getCurrentContext();
		logger.debug("Processing incoming request for {}.", ctx.getRequest().getRequestURI());
		return null;
	}

	@Override
	public String filterType() {
		return FilterUtils.PRE_FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

	private boolean isCorrelationIdPresent() {
		if (filterUtils.getCorrelationId() != null) {
			return true;
		}
		return false;
	}

	private String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}
	
	@Autowired
	private ServiceConfig serviceConfig;

	private String getOrganizationName() {

		String result = "";
		if  ( filterUtils.getAuthToken() != null ) {

			String authToken = filterUtils.getAuthToken().replace("Bearer ", "");
//			System.out.println( "authToken = " + authToken );
			try {
				Claims claims = Jwts.parser().setSigningKey(serviceConfig.getJwtSigningKey().getBytes("UTF-8"))
						.parseClaimsJws(authToken).getBody();
				result = (String) claims.get("organizationName");
			} catch (Exception e) {
				e.printStackTrace();
			}
//		} else {
//			System.out.println( "AuthToken is null." );
		}
		return result;
	}

}
