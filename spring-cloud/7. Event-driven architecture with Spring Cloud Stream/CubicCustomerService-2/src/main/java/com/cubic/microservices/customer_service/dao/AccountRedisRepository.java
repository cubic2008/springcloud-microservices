package com.cubic.microservices.customer_service.dao;

import java.util.List;

import com.cubic.microservices.customer_service.domain.Account;

public interface AccountRedisRepository {
	void saveAccounts(int customerId, List<Account> accounts);
	void updateAccounts(int customerId, List<Account> accounts);
	void deleteAccount(int customerId);
	List<Account> findAccounts(int customerId);
}
