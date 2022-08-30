package com.cubic.microservices.zuul_server.domain;

public class AbTestingRoute {
    String serviceName;
//    String zuulServiceRoute;
    String active;
    String endpoint;
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

	@Override
	public String toString() {
		return "AbTestingRoute [serviceName=" + serviceName + ", active="
				+ active + ", endpoint=" + endpoint + ", weight=" + weight + "]";
	}

}