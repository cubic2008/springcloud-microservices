package com.cubic.microservices.customer_service.events.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.cubic.microservices.customer_service.dao.AccountRedisRepository;
import com.cubic.microservices.customer_service.events.CustomChannels;
import com.cubic.microservices.customer_service.events.domain.AccountChangeModel;
import com.cubic.microservices.customer_service.services.CustomerService;

@EnableBinding(CustomChannels.class)
public class AccountChangeHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private AccountRedisRepository accountRedisRepository;

	@StreamListener("inboundAccountChanges")
	public void loggerSink(AccountChangeModel acctChange) {
		logger.debug("Received an {} event for customer id {} and account id {}", acctChange.getAction(),
				acctChange.getCustomerId(), acctChange.getAccountId());
		switch (acctChange.getAction()) {
			case "GET":
			case "SAVE":
				break;
			case "UPDATE":
			case "DELETE":
				this.accountRedisRepository.deleteAccount(acctChange.getAccountId());
				logger.debug("Account list for customer id {} have been removed from Redis cache", acctChange.getCustomerId() );
				break;
		}
	}

}
