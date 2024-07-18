package com.innovTech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovTech.entity.Transaction;

public interface TransactionDao extends JpaRepository<Transaction, Long> {

}
