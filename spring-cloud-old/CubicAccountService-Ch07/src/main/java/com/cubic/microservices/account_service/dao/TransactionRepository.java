package com.cubic.microservices.account_service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cubic.microservices.account_service.domain.Account;
import com.cubic.microservices.account_service.domain.Transaction;

//@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	public Account findById ( int id );
	
	public List<Transaction> findByAccountId ( int accountId );
	
}	
