package com.cubic.microservices.account_service.events.domain;

public class AccountChangeModel {
	
    private String type;
    private String action;
    private int customerId;
    private int accountId;
    private String correlationId;
    
	public AccountChangeModel(String type, String action, int customerId, int accountId, String correlationId) {
		super();
		this.type = type;
		this.action = action;
		this.customerId = customerId;
		this.accountId = accountId;
		this.correlationId = correlationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
    
}
