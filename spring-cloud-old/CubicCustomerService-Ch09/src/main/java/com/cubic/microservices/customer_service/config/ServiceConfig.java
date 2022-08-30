package com.cubic.microservices.customer_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ServiceConfig {
	
	@Value("${redis.server}")
	private String redisServer = "";

	public String getRedisServer() {
		return this.redisServer;
	}

	@Value("${redis.port}")
	private int redisPort = 0;

	public int getRedisPort() {
		return this.redisPort;
	}

}
