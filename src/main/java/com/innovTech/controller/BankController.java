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

import com.innovTech.controller.model.BankDto;
import com.innovTech.service.BankService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bank")
@Slf4j
public class BankController {
	
	@Autowired
	private BankService bankService;
	
	/* ======================= CREATE = POST ============================  */
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public BankDto insertBank (@RequestBody BankDto bankDto) {
		
		log.info("Creating a bank data {} ", bankDto);
		
		return bankService.saveBank(bankDto);
	}
	
	/* ======================= UPDATE = PUT ============================  */
	
	
	@PutMapping("/{bankDtoId}")
	public BankDto updateBank (
			@PathVariable Long bankDtoId, 
			@RequestBody BankDto bankDto) {
		
		bankDto.setBankId(bankDtoId);
		
		log.info("Updating a bank data {} with ID ", bankDto, bankDtoId);
		
		return bankService.saveBank(bankDto);
	}
	
	/* ======================= READ = GET ============================  */
	
	
	@GetMapping
	public List<BankDto> retrieveAllBanks() {
		
		log.info("Retrieving all banks with employees and customers.");
		
		return bankService.retrieveAllBanks();
	}
	
	@GetMapping("/{bankId}")
	public BankDto retrieveBankById(@PathVariable Long bankId) {
		
		log.info("Retrieving a bank with ID = {} ", bankId);
		
		return bankService.retrieveBankById(bankId);
	}
	
	
	@GetMapping("/onlyBanks")
	public List<BankDto> retrieveAllBanksWithoutEmployeeAndCustomer() {
		
		log.info("Retrieving all banks without employee and customer.");
		
		return bankService.retrieveAllBanksWithoutEmployeeAndCustomer();
	}
	
	
	/* ======================= DELETE = DELETE ============================  */
	
	@DeleteMapping
	public void deleteAllBanks() {
		
		log.info("Attempting to delete all banks.");
		
		throw new UnsupportedOperationException(
				"Deleting all banks is not allowed.");
	}
	
	
	@DeleteMapping("/{bankId}")
	public Map<String, String> deleteBankById(@PathVariable Long bankId) {
		
		log.info("Deleting a bank with ID = {} ", bankId);
		
		bankService.deleteBankById(bankId);
		
		return Map.of(
				"message", "Deleting a bank with ID = " + bankId + " was successful."
				);
	}

}
