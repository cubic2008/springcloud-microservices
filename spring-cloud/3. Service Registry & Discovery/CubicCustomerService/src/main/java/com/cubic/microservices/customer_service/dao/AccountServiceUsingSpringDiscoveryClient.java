package com.cubic.microservices.customer_service.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cubic.microservices.customer_service.domain.Account;

// Option #1: Looking up service instances with Spring DiscoveryClient

@Component
public class AccountServiceUsingSpringDiscoveryClient {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	private static int serviceIndex = 0;

	public List<Account> getAccountsForCustomer(int customerId) {
		System.out.println( "Option #1: AccountServiceDiscoveryClient.getAccounts()");
		RestTemplate restTemplate = new RestTemplate();
		System.out.println ( "discoveryClient.description() = " + discoveryClient.description() );
		List<String> services = discoveryClient.getServices();
		System.out.println ( "services.size() = " + services.size() );
		for ( String service : services ) {
			System.out.println( "service = " + service );
		}
		List<ServiceInstance> instances = discoveryClient.getInstances("ACCOUNT-SERVICE");
		for ( ServiceInstance instance : instances ) {
			System.out.println( "instance = " + instance.getUri().toString() );
		}
		if (instances.size() == 0) {
			System.out.println( "instances.size() == 0" );
			return null;
		}
		
		// Using programmatically backwards round-robin strategy
		serviceIndex --;
		if (serviceIndex < 0 || serviceIndex >= instances.size()) {
			serviceIndex = instances.size() - 1;
		}
		String serviceUri = String.format("%s/v1/accounts/customer/%d", instances.get(serviceIndex).getUri().toString(),
				customerId);
		System.out.println("Invoking accountService: " + serviceUri);
		@SuppressWarnings("unchecked")
		ResponseEntity<List<Account>> restExchange = 
			restTemplate.exchange(serviceUri, HttpMethod.GET, null, 
					(Class<List<Account>>) ((Class)List.class), customerId);
		return restExchange.getBody();
	}
	
}
