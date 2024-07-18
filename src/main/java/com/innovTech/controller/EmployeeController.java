package com.innovTech.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.innovTech.controller.model.BankEmployee;
import com.innovTech.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bank")
@Slf4j
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	
	/* ======================= CREATE = POST ============================  */

	@PostMapping("/{bankId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BankEmployee insertEmployee (
			@PathVariable Long bankId , @RequestBody BankEmployee bankEmployee) {
		
		log.info("Creating an employee {} for bank with ID = {} ", bankEmployee, bankId);
		
		return employeeService.saveEmployee(bankId, bankEmployee);
	}
	
	
	/* ======================= UPDATE = PUT =============================  */
	
	
	@PutMapping("/{bankId}/employee/{employeeId}")
	public BankEmployee updateEmployee(
			@PathVariable Long bankId,
			@PathVariable Long employeeId,
			@RequestBody BankEmployee bankEmployee) {
		
		bankEmployee.setEmployeeId(employeeId);
		
		log.info("Updating an employee {} with ID = {} for bank with ID = {} ", 
				bankEmployee, employeeId, bankId);
		
		return employeeService.saveEmployee(bankId, bankEmployee);
	}
	
	
	/* ======================= DELETE = DELETE =============================  */
	
	@DeleteMapping("/{bankId}/employee")
	public void deleteEmployees(@PathVariable Long bankId) {
		
		log.info("Attempting to delete all employees in bank with ID = {}.", bankId);
		
		throw new UnsupportedOperationException(
				"Deleting all employees is not supprted."
				);
	}
	
	
	@DeleteMapping("/{bankId}/employee/{employeeId}")
	public Map<String, String> deleteEmployeeById(
			@PathVariable Long bankId,
			@PathVariable Long employeeId) {
		
		log.info("Deleting employee with ID = {} in bank with ID = {} ", employeeId, bankId);
		
		employeeService.deleteEmployeeById(bankId, employeeId);
		
		return Map.of(
				"message", "Deleting employee with ID = " + employeeId + " in bank with ID = " +
							bankId + " was successful."
				);
	}
	
	
	/* ========================= READ = GET ================================  */
	
	@GetMapping("/{bankId}/employee")
	public List<BankEmployee> retrieveAllEmployeesInBankId(@PathVariable Long bankId) {
		
		log.info("Retrieving all employees in bank with ID = {} ", bankId);
		
		return employeeService.retrieveAllEmployeesInBankId(bankId);
	}
	
	
	@GetMapping("/{bankId}/employee/{employeeId}")
	public BankEmployee retrieveEmployeeById(
			@PathVariable Long bankId, @PathVariable Long employeeId) {
		
		log.info("Retrieving an employee with ID = {} for bank with ID = {} ", 
			employeeId, bankId);
		
		return employeeService.retrieveEmployeeById(bankId, employeeId);
	}
	

}
