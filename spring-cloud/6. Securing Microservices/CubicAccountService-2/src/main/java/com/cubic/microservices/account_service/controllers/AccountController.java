package com.cubic.microservices.account_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cubic.microservices.account_service.domain.Account;
import com.cubic.microservices.account_service.domain.Transaction;
import com.cubic.microservices.account_service.services.AccountService;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping("")
	public List<Account> getAllAccounts ( ) {
		return this.accountService.getAllAcounts();
	}

	@GetMapping("/customer/{customerId}")
	public List<Account> getAllAccounts ( @PathVariable int customerId ) {
		return this.accountService.getAllAcounts(customerId);
	}

	@GetMapping("{id}")
	public Account getAccount ( @PathVariable int id ) {
		return this.accountService.getAccountById(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	public Account createAccount ( @RequestBody Account account ) {
		System.out.println( "in AccountController.createAccount()");
//		return null;
		return this.accountService.saveAccount ( account );
	}

	@PutMapping("{id}")
	public Account updateAccount ( @PathVariable int id, @RequestBody Account account ) {
		return this.accountService.updateAccount ( id, account );
	}

	@GetMapping("{accountId}/transactions")
	public List<Transaction> getTransactionsByAccount ( @PathVariable int accountId ) {
		return this.accountService.getTransactions(accountId);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("{accountId}/transactions")
	public Transaction newTransaction ( @PathVariable int accountId, @RequestBody Transaction transaction ) {
		transaction.setAccountId(accountId);
		return this.accountService.createTransaction(transaction);
	}
}
