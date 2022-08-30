package com.qualityit.donation.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {
	@RequestMapping(path = { "/", "/hello2" }, method = RequestMethod.GET)
	public String sayHello(Model model) {
		
		System.out.println( "Hello." );
		model.addAttribute("message", "Hello Spring MVC!");

		// Java 8 LocalDate
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		LocalDate date = LocalDate.now();
		model.addAttribute("date", date.format(formatter));

		return "index";
	}
}
