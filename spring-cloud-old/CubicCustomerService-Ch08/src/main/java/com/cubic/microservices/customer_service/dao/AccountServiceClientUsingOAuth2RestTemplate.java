package com.cubic.microservices.customer_service.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.cubic.microservices.customer_service.domain.Account;
import com.cubic.microservices.customer_service.domain.Customer;
import com.cubic.microservices.customer_service.utils.UserContext;
import com.cubic.microservices.customer_service.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Component
public class AccountServiceClientUsingOAuth2RestTemplate {
	
	@Autowired
	OAuth2RestTemplate restTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceClientUsingOAuth2RestTemplate.class);

//	public Organization getOrganization(String organizationId) {
//		logger.debug("In Licensing Service.getOrganization: {}", UserContext.getCorrelationId());
//		ResponseEntity<Organization> restExchange = restTemplate.exchange(
//				"http://zuulserver:5555/api/organization/v1/organizations/{organizationId}", HttpMethod.GET, null,
//				Organization.class, organizationId);
//		/* Save the record from cache */
//		return restExchange.getBody();
//	}

	public List<Account> getAccountsForCustomer(int customerId) {
		logger.debug("In AccountServiceClientUsingOAuth2RestTemplate.getAccountsForCustomer: {}",
				UserContextHolder.getContext().getCorrelationId());
		try {
			System.out.println ( "restTemplate.getAccessToken().getScope() = " + restTemplate.getAccessToken().getScope() );
			System.out.println ( "restTemplate.getAccessToken().getTokenType() = " + restTemplate.getAccessToken().getTokenType() );
			System.out.println ( "restTemplate.getAccessToken().getValue() = " + restTemplate.getAccessToken().getValue() );
			System.out.println ( "restTemplate.getAccessToken().getRefreshToken() = " + restTemplate.getAccessToken().getRefreshToken() );
			@SuppressWarnings("unchecked")
			ResponseEntity<List<Account>> restExchange = restTemplate.exchange(
					// "http://account-service/v1/accounts/customer/{customerId}",
					"http://zuul-service/api/acct-service/v1/accounts/customer/{customerId}",
					// "http://zuul-service/account-service/v1/accounts/customer/{customerId}",
					HttpMethod.GET, null, (Class<List<Account>>) ((Class) List.class), customerId);
			return restExchange.getBody();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@HystrixCommand(fallbackMethod="retrieveNoAccounts",
		commandProperties= {
			@HystrixProperty(
				name = "execution.isolation.strategy", 
				value = "SEMAPHORE") }
	)
	public void retrieveAccounts(Customer customer) {
		System.out.println( "retrieveAccounts(Customer): " + Thread.currentThread().getName() );
//		System.out.println("retrieveAccounts(): customer = " + customer);
		List<Account> accountList = this.getAccountsForCustomer(customer.getId());
		customer.setAccountList(accountList);
	}

	public void retrieveNoAccounts(Customer customer) {
		System.out.println( "retrieveNoAccounts(): " + Thread.currentThread().getName() );
		customer.setAccountList(new ArrayList<Account>());
	}

	public void retrieveNoAccounts(List<Customer> customerList) {
		System.out.println( "retrieveNoAccounts(): " + Thread.currentThread().getName() );
		for (Customer customer : customerList) {
			customer.setAccountList(new ArrayList<Account>());
		}
	}

	@HystrixCommand(fallbackMethod="retrieveNoAccounts",
		commandProperties= {
			@HystrixProperty(
				name = "execution.isolation.strategy", 
				value = "SEMAPHORE") }
	)
	public void retrieveAccounts(List<Customer> customerList) {
		System.out.println( "retrieveAccounts(List<Customer>): " + Thread.currentThread().getName() );
		for (Customer customer : customerList) {
			retrieveAccounts(customer);
		}
	}


}
