package com.cubic.microservices.customer_service.services;

public enum DiscoveryClient {

	SpringDiscoveryClient,
	RibbonAwaredSpringRestTemplate,
	FeignClient;
	
	public static final String SPRING_DISCOVERY_CLIENT = "SpringDiscoveryClient"; 
	public static final String RIBBON_AWARED_SPRING_REST_TEMPLATE = "RibbonAwaredSpringRestTemplate"; 
	public static final String FEIGN_CLIENT = "FeignClient"; 

}
