package com.cubic.microservices.customer_service.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomChannels {
	
	@Input("inboundAccountChanges")
	SubscribableChannel inboundOrgChanges();
}
