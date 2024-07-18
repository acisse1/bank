package com.innovTech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovTech.controller.model.BankEmployee;
import com.innovTech.dao.EmployeeDao;
import com.innovTech.entity.Bank;
import com.innovTech.entity.Employee;


@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private BankService bankService;
	
	
	/* =================== implementing saveEmployee() =======================  */

	@Transactional(readOnly = false)
	public BankEmployee saveEmployee(Long bankId, BankEmployee bankEmployee) {

		Long employeeId = bankEmployee.getEmployeeId();
		
		Employee employee = findOrCreateEmployee(employeeId);
		
		Bank bank = bankService.findBankById(bankId);
		
		
		setFieldsInEmployee(employee, bankEmployee);
		
		employee.setBank(bank);
		bank.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		BankEmployee bankEmployeeDto = new BankEmployee(dbEmployee);
		
		return bankEmployeeDto;
	}

	/* =================== implementing setFieldsInEmployee() =======================  */

	private void setFieldsInEmployee(Employee employee, BankEmployee bankEmployee) {

		employee.setEmployeeId(bankEmployee.getEmployeeId());
		employee.setEmployeeFirstName(bankEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(bankEmployee.getEmployeeLastName());
		employee.setEmployeeEmail(bankEmployee.getEmployeeEmail());
		employee.setEmployeePassword(bankEmployee.getEmployeePassword());
		employee.setEmployeePhone(bankEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(bankEmployee.getEmployeeJobTitle());
	}
	
	/* =================== implementing findOrCreateEmployee() =======================  */

	private Employee findOrCreateEmployee(Long employeeId) {

		Employee employee;
		
		if (Objects.isNull(employeeId)) {
			employee = new Employee();
		}
		
		else {
			employee = findEmployeeById(employeeId);
		}
		
		return employee;
	}
	
	/* =================== implementing findEmployeeById() =======================  */

	private Employee findEmployeeById(Long employeeId) {

		return employeeDao.findById(employeeId).orElseThrow(
				() -> new NoSuchElementException(
						"Employee with ID = " + employeeId + " wsa not found"
						));
	}
	
	/* =================== implementing deleteEmployeeById() =======================  */

	@Transactional(readOnly = false)
	public void deleteEmployeeById(Long bankId, Long employeeId) {

		Employee employee = findEmployeeById(employeeId);
		
		Bank bank = bankService.findBankById(bankId);
		
		if (employee.getBank().getBankId() != bankId) {
			
			throw new IllegalStateException(
					"Employee with ID = " + employeeId + " was not owned by bank with ID = "
					+ bankId);
		}
		
		employeeDao.delete(employee);
	}

	
	/* ================= implementing retrieveAllEmployeesInBankId() ===============  */
	
	@Transactional(readOnly = true)
	public List<BankEmployee> retrieveAllEmployeesInBankId(Long bankId) {

		Bank bank = bankService.findBankById(bankId);
		
		List<BankEmployee> bankEmployees = new ArrayList<BankEmployee>();
		
		BankEmployee bankEmployee;
		
		for (Employee employee : bank.getEmployees()) {
			
			bankEmployee = new BankEmployee(employee);
			bankEmployees.add(bankEmployee);
		}
		return bankEmployees;
	}
	
	/* ================= implementing retrieveEmployeeById() ===============  */

	@Transactional(readOnly = true)
	public BankEmployee retrieveEmployeeById(Long bankId, Long employeeId) {

		Employee employee = findEmployeeById(employeeId);
		
		Bank bank = bankService.findBankById(bankId);
		
		if (employee.getBank().getBankId() != bankId) {
			
			throw new IllegalStateException(
					"Employee with ID = " + employeeId + " was not owned by bank with ID = "
					+ bankId);
		}
		
		BankEmployee bankEmployee = new BankEmployee(employee);
		
		return bankEmployee;
	}

}
