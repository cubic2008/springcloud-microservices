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
import com.cubic.microservices.account_service.utils.UserContextHolder;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	private void updateBetaInfo ( Account account ) {
		account.setAccountNo( "NEW - " + account.getAccountNo() );
	}
	
	private void updateBetaInfo ( List<Account> accounts ) {
		for ( Account account : accounts ) {
			updateBetaInfo ( account );
		}
	}
	@Override
	public List<Account> getAllAcounts() {
		logger.debug("AccountServiceImpl.getAllAcounts() - Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		List<Account> accountList = this.accountRepository.findAll();
		updateBetaInfo ( accountList );
		return accountList;
	}

	@Override
	public Account getAccountById(int id) {
		logger.debug("AccountServiceImpl.getAccountById() - Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		Account account = this.accountRepository.findById(id);
		updateBetaInfo ( account );
		return account;
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
		logger.debug("AccountServiceImpl.getAllAcounts(" + customerId + ") - Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		List<Account> accountList = this.accountRepository.findByCustomerId(customerId);
		updateBetaInfo ( accountList );
		return accountList;
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
