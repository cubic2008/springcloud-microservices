package com.cubic.microservices.zipkin_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableZipkinServer
public class CubicZipkinServerCh09Application {

	public static void main(String[] args) {
		SpringApplication.run(CubicZipkinServerCh09Application.class, args);
	}

}
