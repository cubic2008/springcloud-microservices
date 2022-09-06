package com.cubic.microservices.customer_service.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cubic.microservices.customer_service.domain.Account;
import com.cubic.microservices.customer_service.domain.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

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
	
	@HystrixCommand(fallbackMethod="retrieveNoAccounts")
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

	@HystrixCommand(fallbackMethod="retrieveNoAccounts")
	public void retrieveAccounts(List<Customer> customerList) {
		System.out.println( "retrieveAccounts(List<Customer>): " + Thread.currentThread().getName() );
		for (Customer customer : customerList) {
			retrieveAccounts(customer);
		}
	}



}
