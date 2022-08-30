package com.cubic.microservices.configuration_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CubicConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CubicConfigServerApplication.class, args);
	}
}
