package com.innovTech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovTech.entity.Bank;

public interface BankDao extends JpaRepository<Bank, Long> {

}
