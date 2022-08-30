package com.cubic.microservices.licenseing_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubic.microservices.licenseing_service.config.ServiceConfig;
import com.cubic.microservices.licenseing_service.domain.License;
import com.cubic.microservices.licenseing_service.repository.LicenseRepository;

@Service
public class LicenseService {

	@Autowired
	private LicenseRepository licenseRepository;

	@Autowired
	ServiceConfig config;
	
	public License getLicense(String organizationId, String licenseId) {
		License license = licenseRepository.findByOrganizationIdAndId(organizationId, licenseId);
		System.out.println( "config.getDbURL() = " + config.getDbURL() + ", config.getDbUsername() = " + config.getDbUsername() + ", config.getExamplePropertyEncrypted() = " + config.getExamplePropertyEncrypted() );
		
		return license.withComments(config.getExampleProperty());
//		return license;
	}

	public List<License> getLicensesByOrg(String organizationId) {
		return licenseRepository.findByOrganizationId(organizationId);
	}

	public void saveLicense(License license) {
		license.withId(UUID.randomUUID().toString());
		licenseRepository.save(license);
	}
}
