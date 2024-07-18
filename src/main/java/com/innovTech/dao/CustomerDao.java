package com.innovTech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovTech.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
