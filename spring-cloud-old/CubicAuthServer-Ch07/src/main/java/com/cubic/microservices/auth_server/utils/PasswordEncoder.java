package com.cubic.microservices.auth_server.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	public static void main(String[] args) {
		String plan_tx_passwords[] = { "thisissecret", "password1", "password2" };
		for ( String plan_tx_password : plan_tx_passwords ) {
			String encodedPassword = new BCryptPasswordEncoder().encode(plan_tx_password);
			System.out.println( plan_tx_password + " <---> " + encodedPassword );
		}
	}

}
