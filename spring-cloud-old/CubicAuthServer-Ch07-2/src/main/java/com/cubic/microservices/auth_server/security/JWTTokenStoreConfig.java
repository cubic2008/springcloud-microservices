package com.cubic.microservices.auth_server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.cubic.microservices.auth_server.config.ServiceConfig;

@Configuration
public class JWTTokenStoreConfig {

	@Autowired
	private ServiceConfig serviceConfig;

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//		System.out.println( "serviceConfig.getJwtSigningKey() = " + serviceConfig.getJwtSigningKey() );
		converter.setSigningKey(serviceConfig.getJwtSigningKey());
		System.out.println( "serviceConfig.getJwtSigningKey() = " + serviceConfig.getJwtSigningKey() );
		System.out.println( "converter.getKey() = " + converter.getKey() );
		return converter;
	}

	@Bean
	public TokenEnhancer jwtTokenEnhancer() {
		return new JWTTokenEnhancer();
	}
}