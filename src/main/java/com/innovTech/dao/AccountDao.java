package com.innovTech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovTech.entity.Account;

public interface AccountDao extends JpaRepository<Account, Long> {
	
	

}
