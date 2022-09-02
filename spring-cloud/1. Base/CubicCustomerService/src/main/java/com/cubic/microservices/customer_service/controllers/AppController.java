package com.cubic.microservices.customer_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cubic.microservices.customer_service.domain.AppInfo;
import com.cubic.microservices.customer_service.services.CustomerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/app")
public class AppController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("name")
	public AppInfo getAppName ( ) {
		System.out.println( "In AppController.getAppName()." );
		System.out.println( "appName = " + this.customerService.getAppName() );
		return this.customerService.getAppName();
	}

}
