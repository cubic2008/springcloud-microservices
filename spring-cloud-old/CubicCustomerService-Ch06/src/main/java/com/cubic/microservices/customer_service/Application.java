package com.cubic.microservices.customer_service;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.cubic.microservices.customer_service.utils.UserContextInterceptor;

@SpringBootApplication
//@RefreshScope
//@EnableDiscoveryClient	// Option #1: Looking up service instances with Spring DiscoveryClient
//@EnableFeignClients			// Option #3: Invoking services with Netflix Feign client
@EnableCircuitBreaker
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	// Option #2: Invoking services with Ribbon-aware Spring RestTemplate
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate(){
//		return new RestTemplate();
		RestTemplate template = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
		if (interceptors == null) {
			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		} else {
			interceptors.add(new UserContextInterceptor());
			template.setInterceptors(interceptors);
		}
		return template;
	}
}
