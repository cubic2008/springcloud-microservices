package com.cubic.microservices.customer_service.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.cubic.microservices.customer_service.domain.Account;

@Repository
public class AccountRedisRepositoryImpl implements AccountRedisRepository {

	private static final String HASH_NAME = "Accounts";

	private RedisTemplate<Integer, List<Account>> redisTemplate;

	private HashOperations hashOperations;

	public AccountRedisRepositoryImpl() {
		super();
	}

	@Autowired
	private AccountRedisRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		this.hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void saveAccounts(int customerId, List<Account> accounts) {
		this.hashOperations.put(HASH_NAME, customerId, accounts);
	}

	@Override
	public void updateAccounts(int customerId, List<Account> accounts) {
		this.hashOperations.put(HASH_NAME, customerId, accounts);
	}

	@Override
	public void deleteAccount(int customerId) {
		this.hashOperations.delete(HASH_NAME, customerId);
	}

	@Override
	public List<Account> findAccounts(int customerId) {
		return (List<Account>) hashOperations.get(HASH_NAME, customerId);
	}

}
