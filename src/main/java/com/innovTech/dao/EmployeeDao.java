package com.innovTech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovTech.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
