package com.innovTech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovTech.controller.model.BankDto;
import com.innovTech.dao.BankDao;
import com.innovTech.entity.Bank;



@Service
public class BankService {
	
	@Autowired
	private BankDao bankDao;
	
	/* =================== implementing saveBank() =======================  */

	@Transactional(readOnly = false)
	public BankDto saveBank(BankDto bankDto) {
		
		Long bankId = bankDto.getBankId();
		
		Bank bank = findOrCreateBank(bankId);
		
		setFieldsInBank(bank, bankDto);

		return new BankDto(bankDao.save(bank));
	}
	
	/* =================== implementing setFieldsInBank() =======================  */
	
	private void setFieldsInBank(Bank bank, BankDto bankDto) {

		bank.setBankId(bankDto.getBankId());
		bank.setBankAddress(bankDto.getBankAddress());
		bank.setBankCity(bankDto.getBankCity());
		bank.setBankState(bankDto.getBankState());
		bank.setBankZip(bankDto.getBankZip());
		bank.setBankPhone(bankDto.getBankPhone());
	}

	/* =================== implementing findOrCreateBank() =======================  */

	private Bank findOrCreateBank(Long bankId) {

		Bank bank;
		
		if (Objects.isNull(bankId)) {
			bank = new Bank();
		}
		
		else {
			bank = findBankById(bankId);
		}
		
		return bank;
	}
	
	/* =================== implementing findBankById() =======================  */

	public Bank findBankById(Long bankId) {

		return bankDao.findById(bankId)
				.orElseThrow(
						() -> new NoSuchElementException(
							"Bank with ID = " +  bankId + " was not found."
								));
	}
	
	/* =================== implementing retrieveAllBanks() ====================  */

	@Transactional(readOnly = true)
	public List<BankDto> retrieveAllBanks() {

		List<BankDto> bankDtos = new ArrayList<BankDto>();
		
		BankDto bankDto;
		
		List<Bank> banks = bankDao.findAll();
		
		for (Bank bank : banks) {
			
			bankDto = new BankDto(bank);
			
			bankDtos.add(bankDto);
		}
		
		return bankDtos;
	}
	
	/* =================== implementing retrieveBankById() ====================  */

	@Transactional(readOnly = true)
	public BankDto retrieveBankById(Long bankId) {

		Bank bank = findBankById(bankId);
		
		BankDto bankDto = new BankDto(bank);
		
		return bankDto;
	}
	
	/* ======= implementing retrieveAllBanksWithoutEmployeeAndCustomer() ======  */

	@Transactional(readOnly = true)
	public List<BankDto> retrieveAllBanksWithoutEmployeeAndCustomer() {
		
		List<BankDto> bankDtos = new ArrayList<BankDto>();
		
		BankDto bankDto;
		
		List<Bank> banks = bankDao.findAll();
		
		for (Bank bank : banks) {
			
			bankDto = new BankDto(bank);
			
			bankDto.getEmployees().clear();
			bankDto.getCustomers().clear();
			
			bankDtos.add(bankDto);
		}
		
		return bankDtos;
	}
	
	/* =================== implementing deleteBankById() ====================  */

	
	@Transactional(readOnly = false)
	public void deleteBankById(Long bankId) {

		Bank bank = findBankById(bankId);
		
		bankDao.delete(bank);
	}

}
