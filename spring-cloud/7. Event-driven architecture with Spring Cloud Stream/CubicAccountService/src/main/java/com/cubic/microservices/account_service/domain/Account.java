package com.cubic.microservices.account_service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACCT_ID", nullable = false)
	private int id;
	
	@Column(name = "ACCT_NO", nullable = false)
	private String accountNo;

	@Column(name = "ACCT_TYPE", nullable = false)
	private String type;
	
	@Column(name = "BALANCE", nullable = false)
	private double balance;
	
	@Column(name = "CUST_ID", nullable = false)
	private int customerId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNo=" + accountNo + ", type=" + type + ", balance=" + balance
				+ ", customerId=" + customerId + "]";
	}

}

