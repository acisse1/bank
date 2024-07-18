package com.innovTech.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.innovTech.controller.model.CustomerAccount;
import com.innovTech.service.AccountService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/bank")
@Slf4j
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	/* ======================= CREATE = POST ============================  */
	
	@PostMapping("/{bankId}/customer/{customerId}/account")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomerAccount inertAccount (
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@RequestBody CustomerAccount customerAccount) {
		
		log.info("Creating an account {} for customer with ID = {} for bank with ID = {} ", 
				customerAccount, customerId, bankId);
		
		return accountService.saveAccount(bankId, customerId, customerAccount);
	}
	
	/* ======================= UPDATE = PUT =============================  */
	
	@PutMapping("/{bankId}/customer/{customerId}/account/{accountId}")
	public CustomerAccount updateAccount (
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@PathVariable Long accountId,
			@RequestBody CustomerAccount customerAccount) {
		
		
		log.info("Updating an account {} for customer with ID = {} in bank with ID = {} ", 
				customerAccount, customerId, bankId);	
		
		return accountService.updateAccount(bankId, customerId, accountId, customerAccount);
	}
	
	
	/* ======================= DELETE = DELETE =============================  */
	
	
	@DeleteMapping("/{bankId}/customer/{customerId}/account")
	public void deleteAllAccounts(
			@PathVariable Long bankId,
			@PathVariable Long customerId) {
		
		log.info("Attempting to delete all accounts for customer with ID = {} in bank with ID = {}.",
				customerId, bankId);
		
		throw new UnsupportedOperationException(
				"Deleting all accounts is not supported.");
	}
	
	
	@DeleteMapping("/{bankId}/customer/{customerId}/account/{accountId}")
	public Map<String, String> deleteAccountById(
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@PathVariable Long accountId) {
		
		log.info("Deleting account with ID = {} for customer with ID = {} in bank with ID = {}.",
				accountId, customerId, bankId);
		
		accountService.deleteAccountById(accountId, customerId, bankId);
		
		return Map.of(
				"message", "Deleting account with ID = " + accountId +
				" for customer with ID = " + customerId + " in bank with ID = " +
				bankId + " was successful."
				);
	}
	
	
	/* ======================= READ = GET ==================================  */
	
	@GetMapping("/{bankId}/customer/{customerId}/account")
	public List<CustomerAccount> retrieveAllAccountsForCustomerId (
			@PathVariable Long bankId,
			@PathVariable Long customerId) {
		
		log.info("Retrieving all accounts for customer with ID = {} in bank with ID = {} ",
				customerId, bankId);
		
		return accountService.retrieveAllAccountsForCustomerId(bankId, customerId);
		
	}
	
	
	@GetMapping("/{bankId}/customer/{customerId}/account/{accountId}")
	public CustomerAccount retrieveAccountById (
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@PathVariable Long accountId) {
		
		log.info("Retrieving an account with ID = {} for customer with ID = {} in bank with ID = {} ",
				accountId, customerId, bankId);
		
		return accountService.retrieveAccountById(bankId, customerId, accountId);
		
	}
	
	

}
