package com.cubic.microservices.auth_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
//@EnableOAuth2Sso
public class CubicAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CubicAuthServerApplication.class, args);
	}
}
