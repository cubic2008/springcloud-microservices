package com.cubic.education.course_mgmt.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cubic.education.course_mgmt.domain.Account;

@Repository
public class BankingRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;

	public List<Account> retrieveAllAccount() {

		return this.jdbctemplate.query("SELECT ID, NAME, BALANCE FROM ACCOUNT",
				(rs, rowNum) -> new Account(rs.getInt("ID"), rs.getString("NAME"), rs.getDouble("BALANCE")));
	}

	public JdbcTemplate getJdbctemplate() {
		return jdbctemplate;
	}

	public void setJdbctemplate(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public Account retrieveAccount(int accountId) {
		return this.jdbctemplate.queryForObject("SELECT ID, NAME, BALANCE FROM ACCOUNT WHERE ID = ?",
				new Object[] { accountId },
				(rs, rowNum) -> new Account(rs.getInt("ID"), rs.getString("NAME"), rs.getDouble("BALANCE")));
	}

	public Account createAccount(Account account) {
		KeyHolder holder = new GeneratedKeyHolder();
		this.jdbctemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO ACCOUNT(NAME, BALANCE) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, account.getName());
			ps.setDouble(2, account.getBalance());
			return ps;
		}, holder);

		int newAccountId = holder.getKey().intValue();
		account.setId(newAccountId);
		return account;
	}


}
