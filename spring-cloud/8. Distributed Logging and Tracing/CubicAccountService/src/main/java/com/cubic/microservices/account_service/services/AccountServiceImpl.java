package com.cubic.microservices.account_service.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubic.microservices.account_service.dao.AccountRepository;
import com.cubic.microservices.account_service.dao.TransactionRepository;
import com.cubic.microservices.account_service.domain.Account;
import com.cubic.microservices.account_service.domain.Transaction;
import com.cubic.microservices.account_service.events.source.SimpleSourceBean;
import com.cubic.microservices.account_service.utils.UserContextHolder;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public List<Account> getAllAcounts() {
		logger.debug("AccountServiceImpl.getAllAcounts() - Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		return this.accountRepository.findAll();
	}

	@Override
	public Account getAccountById(int id) {
		logger.debug("AccountServiceImpl.getAccountById() - Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		return this.accountRepository.findById(id);
	}
	
	@Autowired
	SimpleSourceBean simpleSourceBean;

	@Override
	public Account saveAccount(Account account) {
		this.accountRepository.save( account );
//		try {
//			System.out.println( "publishing account change event . . ." );
//			this.simpleSourceBean.publishAccountChange("SAVE", Integer.toString ( account.getId() ) );
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return account;
	}

	@Override
	public Account updateAccount(int id, Account account) {
		account.setId(id);
		Account acct = this.accountRepository.save( account );
		try {
			System.out.println( "publishing account change event . . ." );
			this.simpleSourceBean.publishAccountChange("UPDATE", account.getCustomerId(), account.getId() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acct;
	}
	
	@Override
	public List<Transaction> getAllTransactionsByAccountId ( int accountId ) {
		return this.transactionRepository.findByAccountId(accountId);
	}

	@Override
	public List<Account> getAllAcounts(int customerId) {
		logger.debug("AccountServiceImpl.getAllAcounts(" + customerId + ") - Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
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
