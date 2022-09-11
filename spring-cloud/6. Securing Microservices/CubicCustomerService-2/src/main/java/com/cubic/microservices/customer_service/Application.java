package com.cubic.microservices.customer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
// @RefreshScope
// @EnableDiscoveryClient // Option #1: Looking up service instances with Spring DiscoveryClient
// @EnableFeignClients // Option #3: Invoking services with Netflix Feign client
@EnableCircuitBreaker
//@EnableEurekaClient
@EnableResourceServer
//@EnableOAuth2Client
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Option #2: Invoking services with Ribbon-aware Spring RestTemplate
//	@LoadBalanced
//	@Bean
//	public RestTemplate getRestTemplate() {
//		// return new RestTemplate();
//		RestTemplate template = new RestTemplate();
//		List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
//		if (interceptors == null) {
//			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
//		} else {
//			interceptors.add(new UserContextInterceptor());
//			template.setInterceptors(interceptors);
//		}
//		return template;
//	}

//	@Bean
//	public OAuth2ClientContext oAuth2ClientContext() {
//		AccessTokenRequest atr = new DefaultAccessTokenRequest();
//		return new DefaultOAuth2ClientContext(atr);
//	}

//	@Bean
////	@ConfigurationProperties("security.oauth2.client.client-id")
//	@Primary
//	public ClientCredentialsResourceDetails oauth2RemoteResource() {
//		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
//		details.setClientId("eagleeye");
//		return details;
//	}

	@LoadBalanced
	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
			OAuth2ProtectedResourceDetails details) {
		 return new OAuth2RestTemplate(details, oauth2ClientContext);
//		OAuth2RestTemplate template = new OAuth2RestTemplate(details, oauth2ClientContext);
//		List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
//		if (interceptors == null) {
//			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
//		} else {
//			interceptors.add(new UserContextInterceptor());
//			template.setInterceptors(interceptors);
//		}
//		return template;
	}
}
