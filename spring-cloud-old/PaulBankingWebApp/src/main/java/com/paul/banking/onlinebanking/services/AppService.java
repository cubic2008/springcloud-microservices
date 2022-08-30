package com.paul.banking.onlinebanking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paul.banking.onlinebanking.dao.AppDao;
import com.paul.banking.onlinebanking.domain.AppInfo;

@Service
public class AppService {
	
	@Value("${paul.banking.services}")
	private String providedServices;
	
	@Autowired AppDao dao;
	
	public List<String> getServiceList ( ) {
		
		List<String> serviceList = new ArrayList<>();
		StringTokenizer st = new StringTokenizer ( this.providedServices );
		while ( st.hasMoreTokens() ) {
			serviceList.add( st.nextToken() );
		}
		
		return serviceList;
		
	}
	
	public AppInfo getAppInfo ( ) {
		return this.dao.findById(0).get();
	}

	public String getProvidedServices() {
		return providedServices;
	}

	public void setProvidedServices(String providedServices) {
		this.providedServices = providedServices;
	}

	
}
