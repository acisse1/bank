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

import com.innovTech.controller.model.BankCustomer;
import com.innovTech.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bank")
@Slf4j
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	/* ======================= CREATE = POST ============================  */
	
	@PostMapping("/{bankId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BankCustomer insertCustomer(
			@PathVariable Long bankId, @RequestBody BankCustomer bankCustomer) {
		
		log.info("Creating a customer {} for bank with ID = {} ", bankCustomer, bankId);
		
		return customerService.saveCustomer(bankId, bankCustomer);
	}
	
	
	/* ======================= UPDATE = PUT =============================  */
	
	@PutMapping("/{bankId}/customer/{customerId}")
	public BankCustomer updateCustomer(
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@RequestBody BankCustomer bankCustomer) {
		
		bankCustomer.setCustomerId(customerId);
		
		log.info("Updating a customer {} with ID = {} for bank with ID = {} ", 
				bankCustomer, customerId, bankId);	
		
		return customerService.saveCustomer(bankId, bankCustomer);
	}
	
	/* ======================= DELETE = DELETE =============================  */
	
	
	@DeleteMapping("/{bankId}/customer")
	public void deleteAllCustomers(@PathVariable Long bankId) {
		
		log.info("Attempting to delete all customers in bank with ID = {}.", bankId);
		
		throw new UnsupportedOperationException(
				"Deleting all customers is not supported.");
	}
	
	
	@DeleteMapping("/{bankId}/customer/{customerId}")
	public Map<String, String> deleteCustomerById (
			@PathVariable Long bankId,
			@PathVariable Long customerId) {
		
		log.info("Deleting customer with ID = {} in bank with ID = {} ", customerId, bankId);
		
		customerService.deleteCustomerByID(bankId, customerId);
		
		return Map.of(
				"message", "Deleting customer with ID = " + customerId+ " in bank with ID = " +
						bankId + " was successful."
				);
	}
	
	/* ======================= READ = GET ==================================  */
	
	
	@GetMapping("/{bankId}/customer")
	public List<BankCustomer> retrieveAllCustomers(@PathVariable Long bankId) {
		
		log.info("Retrieving all customers in bank with ID = {} ", bankId);
		
		return customerService.retrieveAllCustomersInBankId(bankId);
	}
	
	@GetMapping("/{bankId}/customer/{customerId}")
	public BankCustomer retrieveCustomerById (
			@PathVariable Long bankId,
			@PathVariable Long customerId) {
		
		log.info("Retrieving customer with ID = {} in bank with ID = {} ", customerId, bankId);
		
		return customerService.retrieveCustomerById(bankId, customerId);
	}

}
