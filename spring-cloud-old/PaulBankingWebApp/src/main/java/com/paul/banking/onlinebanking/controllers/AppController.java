package com.paul.banking.onlinebanking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.banking.onlinebanking.domain.AppInfo;
import com.paul.banking.onlinebanking.services.AppService;

@RestController
public class AppController {
	
	@Autowired AppService service;
	
	@GetMapping ( value="/services", produces=MediaType.APPLICATION_JSON_VALUE )
	public List<String> getSeriviceList ( ) {
		return this.service.getServiceList();
	}

	@GetMapping ( value="/info", produces=MediaType.APPLICATION_JSON_VALUE )
	public AppInfo getAppInfo ( ) {
		return this.service.getAppInfo();
	}

	public AppService getService() {
		return service;
	}

	public void setService(AppService service) {
		this.service = service;
	}
	
	

}
