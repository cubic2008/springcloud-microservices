package com.cubic.microservices.customer_service.dao;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cubic.microservices.customer_service.domain.Account;

// Option #3: Invoking services with Netflix Feign client

@FeignClient("account-service")
public interface AccountServiceUsingFeignClient {

	@RequestMapping (
			method = RequestMethod.GET, 
			value = "/v1/accounts/customer/{customerId}", 
			consumes = "application/json")
	List<Account> getAccountsForCustomer ( @PathVariable("customerId") int customerId );
}
