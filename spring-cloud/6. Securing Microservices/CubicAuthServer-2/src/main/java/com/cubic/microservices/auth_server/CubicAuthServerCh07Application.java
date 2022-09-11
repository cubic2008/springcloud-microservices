package com.cubic.microservices.auth_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
//@EnableOAuth2Sso
public class CubicAuthServerCh07Application {

	public static void main(String[] args) {
		SpringApplication.run(CubicAuthServerCh07Application.class, args);
	}
}
