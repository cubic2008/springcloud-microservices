package com.cubic.microservices.customer_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cubic.microservices.customer_service.domain.Customer;

//@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	public Customer findById ( int id );

}	
