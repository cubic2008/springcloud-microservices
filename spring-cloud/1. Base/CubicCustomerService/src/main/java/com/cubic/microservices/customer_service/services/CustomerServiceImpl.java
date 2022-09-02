package com.cubic.microservices.customer_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubic.microservices.customer_service.dao.CustomerRepository;
import com.cubic.microservices.customer_service.domain.AppInfo;
import com.cubic.microservices.customer_service.domain.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private AppInfo appInfo;

	@Override
	public AppInfo getAppName() {
		return this.appInfo;
	}
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> getAllCustomers() {
		return this.customerRepository.findAll();
	}

	@Override
	public Customer getCustomerById(int id) {
		return this.customerRepository.findById(id);
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
