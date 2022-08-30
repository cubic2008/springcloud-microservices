package com.cubic.microservices.customer_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubic.microservices.customer_service.dao.AccountServiceUsingFeignClient;
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
	
//	Option #2: Invoking services with Ribbon-aware Spring RestTemplate
//	@Autowired
//	private AccountServiceClientUsingRibbonAwaredSpringRestTemplate accountServiceDiscoveryClient;
	
	// Option #3: Invoking services with Netflix Feign client
	@Autowired
	private AccountServiceUsingFeignClient accountServiceDiscoveryClient;

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

	private void retrieveAccounts ( List<Customer> customerList ) {
		for ( Customer customer : customerList ) {
			retrieveAccounts(customer);
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
