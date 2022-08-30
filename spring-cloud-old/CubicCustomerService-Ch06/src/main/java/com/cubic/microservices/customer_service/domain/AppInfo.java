package com.cubic.microservices.customer_service.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppInfo {
	
	@Value("${cubic.app.name}")
	private String appName;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public String toString() {
		return "AppInfo [appName=" + appName + "]";
	}
	
	
}
