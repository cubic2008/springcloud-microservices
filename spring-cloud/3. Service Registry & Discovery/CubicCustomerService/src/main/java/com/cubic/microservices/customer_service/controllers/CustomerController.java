package com.cubic.microservices.customer_service.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cubic.microservices.customer_service.domain.AppInfo;
import com.cubic.microservices.customer_service.domain.Customer;
import com.cubic.microservices.customer_service.services.CustomerService;
import com.cubic.microservices.customer_service.services.DiscoveryClient;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

	@GetMapping("name")
	public AppInfo getAppName ( ) {
		System.out.println( "In AppController.getAppName()." );
		return this.customerService.getAppName();
	}

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("")
	public List<Customer> getAllCustomers ( ) {
		return this.customerService.getAllCustomers();
	}

	@GetMapping("/client/{discoveryClient}")
	public List<Customer> getAllCustomers ( @PathVariable("discoveryClient") String discoveryClientName) {
		System.out.println("discoveryClientName = " + discoveryClientName);
		
		DiscoveryClient discoveryClient;
		switch (discoveryClientName) {
			case DiscoveryClient.SPRING_DISCOVERY_CLIENT:
				discoveryClient = DiscoveryClient.SpringDiscoveryClient;
				break;
			case DiscoveryClient.RIBBON_AWARED_SPRING_REST_TEMPLATE:
				discoveryClient = DiscoveryClient.RibbonAwaredSpringRestTemplate;
				break;
			case DiscoveryClient.FEIGN_CLIENT:
				discoveryClient = DiscoveryClient.FeignClient;
				break;
			default:
				discoveryClient = DiscoveryClient.FeignClient;
				break;
		}
		return this.customerService.getAllCustomers(discoveryClient);
	}

	@GetMapping("{id}")
	public Customer getCustomer ( @PathVariable int id ) {
		return this.customerService.getCustomerById(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
//	public Customer createCustomer ( ) {
	public Customer createCustomer ( @RequestBody Customer customer ) {
		System.out.println( "in CustomerController.createCustomer()");
//		return null;
		return this.customerService.saveCustomer ( customer );
	}

	@PutMapping("{id}")
	public Customer updateCustomer ( @PathVariable int id, @RequestBody Customer customer ) {
		return this.customerService.updateCustomer ( id, customer );
	}
}
