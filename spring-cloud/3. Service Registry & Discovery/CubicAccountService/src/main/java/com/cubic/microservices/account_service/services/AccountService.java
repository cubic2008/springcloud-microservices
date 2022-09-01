package com.cubic.microservices.account_service.services;

import java.util.List;

import com.cubic.microservices.account_service.domain.Account;
import com.cubic.microservices.account_service.domain.Transaction;

public interface AccountService {
	
	public List<Account> getAllAcounts();

	public List<Account> getAllAcounts(int customerId);

	public Account getAccountById(int id);

	public Account saveAccount(Account account);

	public Account updateAccount(int id, Account account);

	public List<Transaction> getAllTransactionsByAccountId(int accountId);

	public Transaction createTransaction(Transaction transaction);

	public List<Transaction> getTransactions(int accountId);

}
