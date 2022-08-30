package com.cubic.microservices.customer_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubic.microservices.customer_service.dao.AccountServiceClientUsingRibbonAwaredSpringRestTemplate;
import com.cubic.microservices.customer_service.dao.CustomerRepository;
import com.cubic.microservices.customer_service.domain.AppInfo;
import com.cubic.microservices.customer_service.domain.Customer;
import com.cubic.microservices.customer_service.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	// @Value("${cubic.app.name}")
	// private String appName;

	@Autowired
	private AppInfo appInfo;

	// Option #1: Looking up service instances with Spring DiscoveryClient
	// @Autowired
	// private AccountServiceUsingSpringDiscoveryClient
	// accountServiceDiscoveryClient;

	// Option #2: Invoking services with Ribbon-aware Spring RestTemplate
	 @Autowired
	 private AccountServiceClientUsingRibbonAwaredSpringRestTemplate
	 accountServiceDiscoveryClient;

	// Option #3: Invoking services with Netflix Feign client
//	@Autowired
//	private AccountServiceUsingFeignClient accountServiceDiscoveryClient;

	@Override
	public AppInfo getAppName() {
		return this.appInfo;
	}

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	@HystrixCommand	(fallbackMethod="retrieveNoCustomerProfiles",
		threadPoolKey = "customerThreadPool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize",value="30"),
			@HystrixProperty(name="maxQueueSize", value="10") },
		commandProperties= {
			@HystrixProperty(
				name="execution.isolation.thread.timeoutInMilliseconds",
				value="3000") }
	)
	public List<Customer> getAllCustomers() {
		System.out.println( "getAllCustomers(): " + Thread.currentThread().getName() );
		logger.debug("CustomerServiceImpl.getAllCustomers() - Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		List<Customer> customers = this.retrieveAllCustomerProfiles();
//		retrieveAccounts(customers);
		this.accountServiceDiscoveryClient.retrieveAccounts(customers);
		return customers;
	}

	private void randomlyRunLong() {
		Random rand = new Random();
		int randomNum = rand.nextInt((3 - 1) + 1) + 1;
		System.out.println( "randomlyRunLong(): randomNum = " + randomNum + " - " + Thread.currentThread().getName() );
		if (randomNum == 3) {
			System.out.println( "Time to delay . . ." );
			try { Thread.sleep(11000); } catch (InterruptedException e) { 
				System.out.println("InterruptedException is caught.");
//				throw new RuntimeException ( "Timedout" );
			}
		}
	}

//	@HystrixCommand(fallbackMethod="retrieveNoCustomerProfiles")
	private List<Customer> retrieveAllCustomerProfiles() {
		randomlyRunLong();
		System.out.println( "retrieveAllCustomerProfiles(): " + Thread.currentThread().getName() );
		return this.customerRepository.findAll();
	}

	private List<Customer> retrieveNoCustomerProfiles() {
		System.out.println( "retrieveNoCustomerProfiles is called. " + Thread.currentThread().getName() );
		return new ArrayList<Customer>();
	}
	
//	public void retrieveAccounts(Customer customer) {
//		System.out.println( "retrieveAccounts(): " + Thread.currentThread().getName() );
////		System.out.println("retrieveAccounts(): customer = " + customer);
//		List<Account> accountList = this.accountServiceDiscoveryClient.getAccountsForCustomer(customer.getId());
//		customer.setAccountList(accountList);
//	}
//
//	public void retrieveNoAccounts(Customer customer) {
//		System.out.println( "retrieveNoAccounts(): " + Thread.currentThread().getName() );
//		customer.setAccountList(new ArrayList<Account>());
//	}
//
//	@HystrixCommand(fallbackMethod="retrieveNoAccounts")
//	public void retrieveAccounts(List<Customer> customerList) {
//		for (Customer customer : customerList) {
//			retrieveAccounts(customer);
//		}
//	}

	@Override
	public Customer getCustomerById(int id) {
		Customer customer = this.customerRepository.findById(id);
		System.out.println("getCustomerById(): customer = " + customer);
//		this.accountServiceDiscoveryClient.retrieveAccounts(customer);
		return customer;
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return this.customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(int id, Customer customer) {
		customer.setId(id);
		return this.customerRepository.save(customer);
	}

}
