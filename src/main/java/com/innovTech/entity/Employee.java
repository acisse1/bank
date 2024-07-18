package com.innovTech.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Long employeeId;
	
	@Column(name = "employee_first_name")
	private String employeeFirstName;
	
	@Column(name = "employee_last_name")
	private String employeeLastName;
	
	@Column(name = "employee_email")
	private String employeeEmail;
	
	@Column(name = "employee_phone")
	private String employeePhone;
	
	@Column(name = "employee_password")
	private String employeePassword;
	
	@Column(name = "employee_job_title")
	private String employeeJobTitle;
	
	@ManyToOne(cascade = CascadeType.PERSIST) // "ALL" delete all the banks. 
	@JoinColumn(name = "bank_id")			  // "PERSIST" delete only employee.
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Bank bank;
}
