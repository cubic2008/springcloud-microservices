package com.cubic.microservices.licenseing_service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cubic.microservices.licenseing_service.domain.License;

@Repository
public interface LicenseRepository extends CrudRepository<License,String> {

	public List<License> findByOrganizationId(String organizationId);
	public License findByOrganizationIdAndId(String organizationId,String licenseId);
}
