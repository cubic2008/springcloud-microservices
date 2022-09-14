package com.cubic.microservices.account_service.events.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.cubic.microservices.account_service.events.domain.AccountChangeModel;
import com.cubic.microservices.account_service.utils.UserContextHolder;

@Component
public class SimpleSourceBean {
	private Source source;
	private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

	@Autowired
	public SimpleSourceBean(Source source) {
		this.source = source;
	}

	public void publishAccountChange(String action, int custId, int acctId) {
		logger.debug("Sending Kafka message {} for customer id: {} and account Id: {}", action, custId, acctId);
		AccountChangeModel change = new AccountChangeModel(
//				"com.cubic.microservices.customer_service.events.domain.AccountChangeModel",
				AccountChangeModel.class.getTypeName(),
				action, custId, acctId, UserContextHolder.getContext().getCorrelationId());
		source.output().send(MessageBuilder.withPayload(change).build());
	}
}
