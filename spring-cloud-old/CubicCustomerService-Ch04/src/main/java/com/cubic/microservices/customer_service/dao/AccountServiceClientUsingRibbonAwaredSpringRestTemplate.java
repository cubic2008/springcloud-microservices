package com.cubic.microservices.customer_service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cubic.microservices.customer_service.domain.Account;

// Option #2: Invoking services with Ribbon-aware Spring RestTemplate

@Component
public class AccountServiceClientUsingRibbonAwaredSpringRestTemplate {

	@Autowired
	private RestTemplate restTemplate;

	public List<Account> getAccountsForCustomer(int customerId) {
		System.out.println( "Option #2: AccountServiceDiscoveryClient.getAccounts()");
		@SuppressWarnings("unchecked")
		ResponseEntity<List<Account>> restExchange = 
			restTemplate.exchange(
					"http://account-service/v1/accounts/customer/{customerId}", 
					HttpMethod.GET, null, 
					(Class<List<Account>>) ((Class)List.class), 
					customerId);
		return restExchange.getBody();
	}

}
