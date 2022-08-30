package com.cubic.education.course_mgmt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cubic.education.course_mgmt.dao.BankingRepository;
import com.cubic.education.course_mgmt.domain.Account;
import com.cubic.education.course_mgmt.domain.Course;
import com.cubic.education.course_mgmt.services.CourseService;

@RestController
@RequestMapping("/vic-angular/banking")
@CrossOrigin("*")
public class BankingController {
	
	@Autowired
	private BankingRepository dao;
	
	@RequestMapping ( value = "/accounts", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Account> getAccounts ( ) {
		return this.dao.retrieveAllAccount();
	}
	
	@RequestMapping ( value = "/accounts/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Account getAccount ( @PathVariable("id") int accountId ) {
		
		return this.dao.retrieveAccount(accountId);
		
	}
	
	@RequestMapping ( value = "/accounts", method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Account createAccount ( @RequestBody Account account ) {
		return this.dao.createAccount(account);
	}

	public BankingRepository getDao() {
		return dao;
	}

	public void setDao(BankingRepository dao) {
		this.dao = dao;
	}
	
}
