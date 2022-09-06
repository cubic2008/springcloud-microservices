package com.cubic.microservices.customer_service.services;

import java.util.List;

import com.cubic.microservices.customer_service.domain.AppInfo;
import com.cubic.microservices.customer_service.domain.Customer;

public interface CustomerService {
	
	public AppInfo getAppName ( );

	public List<Customer> getAllCustomers();

	public Customer getCustomerById(int id);

	public Customer saveCustomer(Customer customer);

	public Customer updateCustomer(int id, Customer customer);

}
