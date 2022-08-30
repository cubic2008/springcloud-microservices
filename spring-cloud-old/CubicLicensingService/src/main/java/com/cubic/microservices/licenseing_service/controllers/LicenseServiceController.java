package com.cubic.microservices.licenseing_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cubic.microservices.licenseing_service.domain.License;
import com.cubic.microservices.licenseing_service.services.LicenseService;

@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
	
	@Autowired
	LicenseService licenseService;

	@RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
	public License getLicenses ( @PathVariable("organizationId") String organizationId, @PathVariable("licenseId" ) String licenseId ) {
//		return new License ( ).withId(licenseId).withProductName("Teleco").withLicenseType("Seat").withOrganizationid("TestOrg");
		License license = licenseService.getLicense(organizationId, licenseId);
		return license;
	}
}
