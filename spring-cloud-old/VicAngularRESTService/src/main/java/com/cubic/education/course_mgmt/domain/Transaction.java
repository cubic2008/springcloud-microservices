package com.cubic.education.course_mgmt.domain;

public class Transaction {
	
	private int id;
	private TransactionType type;
	private double amount;
	
	public enum TransactionType {
		Deposity ( "D", "Deposit" ),
		Withdraw ( "W", "Withdraw" );
		
		private TransactionType(String code, String name) {
			this.code = code;
			this.name = name;
		}
		private String code;
		private String name;
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public Transaction() { }

	public Transaction(int id, TransactionType type, double amount) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", type=" + type + ", amount=" + amount + "]";
	}
	
}
