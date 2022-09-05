package com.cubic.microservices.customer_service.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubic.microservices.customer_service.dao.AccountServiceClientUsingRibbonAwaredSpringRestTemplate;
import com.cubic.microservices.customer_service.dao.AccountServiceUsingFeignClient;
import com.cubic.microservices.customer_service.dao.AccountServiceUsingSpringDiscoveryClient;
import com.cubic.microservices.customer_service.dao.CustomerRepository;
import com.cubic.microservices.customer_service.domain.Account;
import com.cubic.microservices.customer_service.domain.AppInfo;
import com.cubic.microservices.customer_service.domain.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	
//	@Value("${cubic.app.name}")
//	private String appName;
	
	@Autowired
	private AppInfo appInfo;
	
//	Option #1: Looking up service instances with Spring DiscoveryClient
//	@Autowired
//	private AccountServiceUsingSpringDiscoveryClient accountServiceDiscoveryClient;
	@Autowired
	private AccountServiceUsingSpringDiscoveryClient accountServiceUsingSpringDiscoveryClient;
	
//	Option #2: Invoking services with Ribbon-aware Spring RestTemplate
//	@Autowired
//	private AccountServiceClientUsingRibbonAwaredSpringRestTemplate accountServiceDiscoveryClient;
	@Autowired
	private AccountServiceClientUsingRibbonAwaredSpringRestTemplate accountServiceClientUsingRibbonAwaredSpringRestTemplate;
	
	// Option #3: Invoking services with Netflix Feign client
	@Autowired
	private AccountServiceUsingFeignClient accountServiceDiscoveryClient;
	@Autowired
	private AccountServiceUsingFeignClient accountServiceUsingFeignClient;

	@Override
	public AppInfo getAppName() {
		return this.appInfo;
	}

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers =  this.customerRepository.findAll();
		retrieveAccounts ( customers );
		return customers;
	}

	@Override
	public List<Customer> getAllCustomers(DiscoveryClient discoveryClient) {
		List<Customer> customers =  this.customerRepository.findAll();
		retrieveAccounts ( customers, discoveryClient );
		return customers;
	}
	@Override
	public Customer getCustomerById(int id) {
		Customer customer = this.customerRepository.findById(id);
		System.out.println( "getCustomerById(): customer = " + customer );
		retrieveAccounts(customer);
		return customer;
	}
	
	private void retrieveAccounts ( Customer customer ) {
		System.out.println( "retrieveAccounts(): customer = " + customer );
		List<Account> accountList = this.accountServiceDiscoveryClient.getAccountsForCustomer( customer.getId() );
		customer.setAccountList(accountList);
	}

	private void retrieveAccounts ( Customer customer, DiscoveryClient discoveryClient ) {
		System.out.format( "retrieveAccounts(): customer = %s (using %s)\n", customer, discoveryClient.name() );
		List<Account> accountList;
		switch (discoveryClient) {
			case SpringDiscoveryClient:
				accountList = this.accountServiceUsingSpringDiscoveryClient.getAccountsForCustomer( customer.getId() );
				break;
			case RibbonAwaredSpringRestTemplate:
				accountList = this.accountServiceClientUsingRibbonAwaredSpringRestTemplate.getAccountsForCustomer( customer.getId() );
				break;
			case FeignClient:
				accountList = this.accountServiceUsingFeignClient.getAccountsForCustomer( customer.getId() );
				break;
			default:
				accountList = new ArrayList<>();
		}
		customer.setAccountList(accountList);
	}

	private void retrieveAccounts ( List<Customer> customerList ) {
		for ( Customer customer : customerList ) {
			retrieveAccounts(customer);
		}
	}

	private void retrieveAccounts ( List<Customer> customerList, DiscoveryClient discoveryClient ) {
		for ( Customer customer : customerList ) {
			retrieveAccounts(customer, discoveryClient);
		}
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return this.customerRepository.save( customer );
	}

	@Override
	public Customer updateCustomer(int id, Customer customer) {
		customer.setId(id);
		return this.customerRepository.save( customer );
	}
	
}
