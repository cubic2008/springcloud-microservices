package com.cubic.microservices.licenseing_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {
	@Value("${example.property}")
	private String exampleProperty;

	public String getExampleProperty() {
		return exampleProperty;
	}
	
	@Value("${spring.datasource.username}")
	private String dbUsername;
	
	@Value("${spring.datasource.url}")
	private String dbURL;

	public String getDbUsername() {
		return dbUsername;
	}

	public String getDbURL() {
		return dbURL;
	}
	
	@Value("${example.property.encrypted}")
	private String examplePropertyEncrypted;

	public String getExamplePropertyEncrypted() {
		return examplePropertyEncrypted;
	}
	
	
	
}
