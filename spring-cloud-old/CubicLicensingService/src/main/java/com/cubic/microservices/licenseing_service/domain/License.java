package com.cubic.microservices.licenseing_service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="licenses")
public class License {
	@Id
	@Column(name="LIC_ID", nullable=false)
	private String id;
	
	@Column(name="PRODUCT_NAME", nullable=false)
	private String productionName;
	
	@Column(name="LIC_TYPE", nullable=false)
	private String licenseType;
	
	@Column(name="ORG_ID", nullable=false)
	private String organizationId;
	
	@Transient
	private String comments;
	
	public License() { }
	
	public License(String id, String productionName, String licenseType, String organizationid) {
		this.id = id;
		this.productionName = productionName;
		this.licenseType = licenseType;
		this.organizationId = organizationid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public String getLicenseId() {
//		return id;
//	}
//
//	public void setLicenseId(String id) {
//		this.id = id;
//	}

	public String getProductionName() {
		return productionName;
	}

	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public License withId ( String id ) {
		this.setId(id);
		return this;
	}
	
	public License withProductName ( String productName ) {
		this.productionName = productName;
		return this;
	}
	
	public License withLicenseType(String licenseType) {
		this.licenseType = licenseType;
		return this;
	}

	public License withOrganizationid(String organizationId) {
		this.organizationId = organizationId;
		return this;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public License withComments ( String comments ) {
		this.comments = comments;
		return this;
	}
}
