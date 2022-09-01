package com.cubic.microservices.customer_service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppInfo {
	
	@Value("${cubic.app.name}")
	private String appName;
	
	@Autowired
	private Environment env;

	public String getAppName() {
		String newAppName = this.env.getProperty( "cubic.app.name" );
		return "OLD: " + appName + ", NEW: " + newAppName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public String toString() {
		return "AppInfo [appName=" + appName + "]";
	}
	
	
}
