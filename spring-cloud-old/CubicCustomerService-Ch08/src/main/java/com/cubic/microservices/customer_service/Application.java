package com.cubic.microservices.customer_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.cubic.microservices.customer_service.config.ServiceConfig;
import com.cubic.microservices.customer_service.events.domain.AccountChangeModel;
import com.cubic.microservices.customer_service.services.CustomerService;

@SpringBootApplication
// @RefreshScope
// @EnableDiscoveryClient // Option #1: Looking up service instances with Spring
// DiscoveryClient
// @EnableFeignClients // Option #3: Invoking services with Netflix Feign client
@EnableCircuitBreaker
// @EnableEurekaClient
@EnableResourceServer
// @EnableOAuth2Client
@EnableBinding(Sink.class)
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@StreamListener(Sink.INPUT)
	public void loggerSink(AccountChangeModel acctChange) {
		logger.debug("Received an event for customer id {} and account id {}", acctChange.getCustomerId(),
				acctChange.getAccountId());
	}
//	 public void loggerSink ( String s ) {
//	 logger.debug("Received an event for account id {}" , s );
//	 }

	@Autowired
	private ServiceConfig serviceConfig;

	// All other methods in the class have been removed for consiceness
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
		jedisConnFactory.setHostName(serviceConfig.getRedisServer());
		jedisConnFactory.setPort(serviceConfig.getRedisPort());
		return jedisConnFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Option #2: Invoking services with Ribbon-aware Spring RestTemplate
	// @LoadBalanced
	// @Bean
	// public RestTemplate getRestTemplate() {
	// // return new RestTemplate();
	// RestTemplate template = new RestTemplate();
	// List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
	// if (interceptors == null) {
	// template.setInterceptors(Collections.singletonList(new
	// UserContextInterceptor()));
	// } else {
	// interceptors.add(new UserContextInterceptor());
	// template.setInterceptors(interceptors);
	// }
	// return template;
	// }

	// @Bean
	// public OAuth2ClientContext oAuth2ClientContext() {
	// AccessTokenRequest atr = new DefaultAccessTokenRequest();
	// return new DefaultOAuth2ClientContext(atr);
	// }

	// @Bean
	//// @ConfigurationProperties("security.oauth2.client.client-id")
	// @Primary
	// public ClientCredentialsResourceDetails oauth2RemoteResource() {
	// ClientCredentialsResourceDetails details = new
	// ClientCredentialsResourceDetails();
	// details.setClientId("eagleeye");
	// return details;
	// }

	@LoadBalanced
	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
			OAuth2ProtectedResourceDetails details) {
		return new OAuth2RestTemplate(details, oauth2ClientContext);
		// OAuth2RestTemplate template = new OAuth2RestTemplate(details,
		// oauth2ClientContext);
		// List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
		// if (interceptors == null) {
		// template.setInterceptors(Collections.singletonList(new
		// UserContextInterceptor()));
		// } else {
		// interceptors.add(new UserContextInterceptor());
		// template.setInterceptors(interceptors);
		// }
		// return template;
	}
}
