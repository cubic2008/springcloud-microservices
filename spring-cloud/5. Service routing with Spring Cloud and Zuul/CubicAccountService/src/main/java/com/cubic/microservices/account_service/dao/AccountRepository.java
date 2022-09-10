package com.cubic.microservices.account_service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cubic.microservices.account_service.domain.Account;

//@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	public Account findById ( int id );
	
	public List<Account> findByCustomerId ( int customerId );

}	
