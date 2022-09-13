package com.cubic.microservices.auth_server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
//		return new StandardPasswordEncoder();
//		return new PasswordEncoder() {
//			
//			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder ( ); 
//
//			@Override
//			public String encode(CharSequence rawPassword) {
////				System.out.println( "PasswordEncoder(): rawPassword = " + rawPassword );
//				return encoder.encode( rawPassword );
////				return null;
//			}
//
//			@Override
//			public boolean matches(CharSequence rawPassword, String encodedPassword) {
//				System.out.println( "PasswordEncoder(): rawPassword = " + rawPassword + ", encodedPassword = " + encodedPassword );
//				return encodedPassword.equals ( encode(rawPassword) );
////				return true;
//			}
//			
//		};
	}
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
//	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
//			.passwordEncoder(encoder) 
//			.passwordEncoder(NoOpPasswordEncoder.getInstance()) 
//			.passwordEncoder(passwordEncoder()) 
				.withUser("john.smith")
//				.password("password1")
				.password(passwordEncoder().encode("password1"))
				.roles("USER")
			.and()
				.withUser("mary.poppins")
//				.password("password2")
				.password(passwordEncoder().encode("password2"))
				.roles("USER", "ADMIN");
	}
}
