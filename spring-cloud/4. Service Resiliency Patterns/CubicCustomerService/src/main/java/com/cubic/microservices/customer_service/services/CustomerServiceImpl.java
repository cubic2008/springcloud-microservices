package com.cubic.microservices.customer_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubic.microservices.customer_service.dao.AccountServiceClientUsingRibbonAwaredSpringRestTemplate;
import com.cubic.microservices.customer_service.dao.CustomerRepository;
import com.cubic.microservices.customer_service.domain.Account;
import com.cubic.microservices.customer_service.domain.AppInfo;
import com.cubic.microservices.customer_service.domain.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class CustomerServiceImpl implements CustomerService {

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
	 private AccountServiceClientUsingRibbonAwaredSpringRestTemplate accountServiceDiscoveryClient;

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
				value="3000"),	// if you change this value to "12000", you’ll never get a timeout error because your artificial timeout on the call is 11 seconds.
			@HystrixProperty(
				name="execution.isolation.thread.interruptOnTimeout",
				value="true"),	// if set the value to "true", Hystrix interrupt (by throwing an InterruptException) to the this.retrieveAllCustomerProfiles() method that causes the delay () and continue to call the rest methods (i.e., this.accountServiceDiscoveryClient.retrieveAccounts(customers)) in the getAllCustomers() method.
//			@HystrixProperty(
//		        name="execution.isolation.strategy",
//        		// using semaphore-isolation, when the timeout occurs, the original getAllCustomers() method will continue to run, 
//		        // as Hystrix is not able to kill the thread within which the this.retrieveAllCustomerProfiles() method (and the 
//		        // randomlyRunLong() method) run. This is because all methods invoked by the getAllCustomers() method are running
//		        // under the same thread.
//		        // If you change to thread-isloation, Hystrix is able to kill the thread that causes the timeout (in this case, it
//		        // is the this.retrieveAllCustomerProfiles() method, which calls randomlyRunLong() method)
//		        // In both isolation cases, the fallback method is called, an empty customer list is returned.
////		        value="SEMAPHORE") 
	})
	public List<Customer> getAllCustomers() {
		System.out.println( "getAllCustomers(): " + Thread.currentThread().getName() );
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
