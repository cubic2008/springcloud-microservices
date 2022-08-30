package com.paul.banking.onlinebanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paul.banking.onlinebanking.domain.AppInfo;

@Repository
public interface AppDao extends JpaRepository<AppInfo, Integer> {

}
