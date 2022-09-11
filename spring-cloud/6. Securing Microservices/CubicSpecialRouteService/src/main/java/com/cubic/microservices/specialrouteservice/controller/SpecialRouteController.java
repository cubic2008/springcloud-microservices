package com.cubic.microservices.specialrouteservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cubic.microservices.specialrouteservice.domain.AbTestingRoute;
import com.cubic.microservices.specialrouteservice.services.AbTestingRouteService;

@RestController
@RequestMapping("/v1/route")
public class SpecialRouteController {
	
	@Autowired
	AbTestingRouteService service;

	@GetMapping ( "/abtesting/{serviceName}" )
	public AbTestingRoute getSpecialRouteByServiceName ( @PathVariable("serviceName") String serviceName ) {
		System.out.println( "serviceName = " + serviceName );
		return this.service.getRoute(serviceName);
	}
	
}
