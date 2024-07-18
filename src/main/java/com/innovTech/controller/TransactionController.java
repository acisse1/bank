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

import com.innovTech.controller.model.AccountTransaction;
import com.innovTech.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bank")
@Slf4j
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	
	/* ======================= CREATE = POST ============================  */
	
	
	@PostMapping("/{bankId}/customer/{customerId}/account/{accountId}/transaction")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AccountTransaction insertTransaction (
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@PathVariable Long accountId,
			@RequestBody AccountTransaction accountTransaction) {
		
		log.info("Creating a transaction {} in account with ID = {} for customer with ID = {} for bank with ID = {} ", 
				accountTransaction, accountId, customerId, bankId);
		
		return transactionService.saveTransaction(bankId, customerId, accountId, accountTransaction);
			
	}
	
	/* ========================== UPDATE = PUT ===============================  */
	
	@PutMapping("/{bankId}/customer/{customerId}/account/{accountId}/transaction/{transactionId}")
	public AccountTransaction upAccountTransaction (
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@PathVariable Long accountId,
			@PathVariable Long transactionId,
			@RequestBody AccountTransaction accountTransaction) {
		
		accountTransaction.setTransactionId(transactionId);
		
		log.info("Updating a transaction with ID = {} in account with ID = {} for customer with ID = {} for bank with ID = {} ", 
				transactionId, accountId, customerId, bankId);
		
		return transactionService.saveTransaction(bankId, customerId, accountId, accountTransaction);
	}
	
	/* ========================== DELETE = DELETE ===============================  */
	
	@DeleteMapping("/{bankId}/customer/{customerId}/account/{accountId}/transaction")
	public void deleteAllTransactionInAccountId(
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@PathVariable Long accountId) {
		
		log.info("Attempting to delete all transactions for account with ID = {} for customer with ID = {} in bank with ID = {}.",
				accountId, customerId, bankId);
		
		throw new UnsupportedOperationException(
				"Deleting all transactions is not supported.");
	}
	
	
	@DeleteMapping("/{bankId}/customer/{customerId}/account/{accountId}/transaction/{transactionId}")
	public Map<String, String> deleteTransactionById (
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@PathVariable Long accountId,
			@PathVariable Long transactionId) {
		
		log.info("Deleting a transaction with ID = {} in account with ID = {} for customer with ID = {} for bank with ID = {} ", 
				transactionId, accountId, customerId, bankId);
		
		transactionService.deleteTransactionById(bankId, customerId, accountId, transactionId);
		
		return Map.of(
				"message", "Deleting transaction with ID = " + transactionId +
				" for customer with ID = " + customerId + " in bank with ID = " +
				bankId + " was successful."
				);
		
	}

	
	/* ============================= READ = GET ======================================  */
	
	
	@GetMapping("/{bankId}/customer/{customerId}/account/{accountId}/transaction")
	public List<AccountTransaction> retrieveAllTransactionInAccountId (
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@PathVariable Long accountId) {
		
		log.info("Retrieving all transactions for account with ID = {} for customer with ID = {} in bank with ID = {} ",
				accountId, customerId, bankId);
		
		return transactionService.retrieveAllTransactionInAccountId(bankId, customerId,accountId);
	}
	
		
	@GetMapping("/{bankId}/customer/{customerId}/account/{accountId}/transaction/{transactionId}")
	public AccountTransaction retrieveTransactionById (
			@PathVariable Long bankId,
			@PathVariable Long customerId,
			@PathVariable Long accountId,
			@PathVariable Long transactionId) {
		
		log.info("Retrieving transaction with ID = {} for account with ID = {} for customer with ID = {} in bank with ID = {} ",
				transactionId, accountId, customerId, bankId);
		
		return transactionService.retrieveTransactionById(bankId, customerId,accountId, transactionId);
	}
}
