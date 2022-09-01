package com.cubic.microservices.account_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubic.microservices.account_service.dao.AccountRepository;
import com.cubic.microservices.account_service.dao.TransactionRepository;
import com.cubic.microservices.account_service.domain.Account;
import com.cubic.microservices.account_service.domain.Transaction;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public List<Account> getAllAcounts() {
		return this.accountRepository.findAll();
	}

	@Override
	public Account getAccountById(int id) {
		return this.accountRepository.findById(id);
	}

	@Override
	public Account saveAccount(Account account) {
		return this.accountRepository.save( account );
	}

	@Override
	public Account updateAccount(int id, Account account) {
		account.setId(id);
		return this.accountRepository.save( account );
	}
	
	@Override
	public List<Transaction> getAllTransactionsByAccountId ( int accountId ) {
		return this.transactionRepository.findByAccountId(accountId);
	}

	@Override
	public List<Account> getAllAcounts(int customerId) {
		return this.accountRepository.findByCustomerId(customerId);
	}

	@Override
	public Transaction createTransaction(Transaction transaction) {
		Transaction newTransaction = this.transactionRepository.save( transaction );
		Account account = this.accountRepository.findById( transaction.getAccountId() );
		int balanceUpdateSign = 1;
//		DE-Deposit, WD-Withdraw, IN-Interest, ID-Initial Deposit, BP-Bill Payment, TI-Transfer In, TO-Transfer Out 
		if ( "WD".equalsIgnoreCase(transaction.getType() ) || "BP".equalsIgnoreCase( transaction.getType() ) || "TD".equalsIgnoreCase( transaction.getType() ) ) {
			balanceUpdateSign = -1;
		}
		account.setBalance( account.getBalance() + transaction.getAmount() * balanceUpdateSign );
		this.accountRepository.save ( account );
		return newTransaction;
	}

	@Override
	public List<Transaction> getTransactions(int accountId) {
		return this.transactionRepository.findByAccountId(accountId);
	}
	
}
