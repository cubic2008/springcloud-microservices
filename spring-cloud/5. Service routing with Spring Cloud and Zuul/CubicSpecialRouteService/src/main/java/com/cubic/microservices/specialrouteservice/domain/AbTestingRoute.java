package com.cubic.microservices.specialrouteservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "abtesting")
public class AbTestingRoute {
    @Id
    @Column(name = "service_name", nullable = false)
    String serviceName;

//    @Column(name = "zuul_service_route", nullable = false)
//    String zuulServiceRoute;

    @Column(name="active", nullable = false)
    String active;
    
    @Column(name = "endpoint", nullable = false)
    String endpoint;
    
    @Column(name = "weight", nullable = false)
    Integer weight;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

//	public String getZuulServiceRoute() {
//		return zuulServiceRoute;
//	}
//
//	public void setZuulServiceRoute(String zuulServiceRoute) {
//		this.zuulServiceRoute = zuulServiceRoute;
//	}


}