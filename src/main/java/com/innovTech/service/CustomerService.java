package com.innovTech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovTech.controller.model.BankCustomer;
import com.innovTech.dao.CustomerDao;
import com.innovTech.entity.Bank;
import com.innovTech.entity.Customer;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private BankService bankService;
	
	/* =================== implementing saveEmployee() =======================  */

	@Transactional(readOnly = false)
	public BankCustomer saveCustomer(Long bankId, BankCustomer bankCustomer) {

		Bank bank = bankService.findBankById(bankId);
		
		Long customerId = bankCustomer.getCustomerId();	
		
		Customer customer = findOrCreateCustomer(customerId, bankId);
		
		setFieldsInCustomer(customer, bankCustomer);
		
		customer.getBanks().add(bank);
		bank.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		BankCustomer customerDto = new BankCustomer(dbCustomer);
		
		return customerDto;
	}
	
	/* =================== implementing setFieldsInCustomer() =======================  */
	
	private void setFieldsInCustomer(Customer customer, BankCustomer bankCustomer) {

		customer.setCustomerId(bankCustomer.getCustomerId());
		customer.setCustomerFirstName(bankCustomer.getCustomerFirstName());
		customer.setCustomerLastName(bankCustomer.getCustomerLastName());
		customer.setCustomerEmail(bankCustomer.getCustomerEmail());
		customer.setCustomerPassword(bankCustomer.getCustomerPassword());
	}

	/* =================== implementing findOrCreateCustomer() ===============  */

	private Customer findOrCreateCustomer(Long customerId, Long bankId) {

		Customer customer;
		
		if (Objects.isNull(customerId)) {
			customer = new Customer();
		}
		
		else {
			customer = findCustomerById(customerId, bankId);
		}
		
		return customer;
	}
	
	/* =================== implementing findCustomerById() ===================  */

	public Customer findCustomerById(Long customerId, Long bankId) {

		Customer customer = customerDao.findById(customerId).orElseThrow(
				() -> new NoSuchElementException(
						"Customer with ID = " + customerId + " was not found"
						));
		
		boolean found = false;
		
		for (Bank bank : customer.getBanks()) {
			
			if (bank.getBankId() == bankId) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			throw new IllegalArgumentException(
					"Customer with ID = " + customerId + " is not a member of "
							+ "bank with ID = " + bankId);
		}
		
		return customer;
	}
	
	/* =================== implementing deleteCustomerByID() ===================  */

	public void deleteCustomerByID(Long bankId, Long customerId) {
		
		Bank bank = bankService.findBankById(bankId);
		Customer customer = findCustomerById(customerId, bankId);
		
		bank.getCustomers().remove(customer);
		customerDao.delete(customer);
	}
	
	/* =================== implementing retrieveAllCustomersInBankId() ===================  */

	public List<BankCustomer> retrieveAllCustomersInBankId(Long bankId) {

		List<BankCustomer> bankCustomers = new ArrayList<BankCustomer>();
		BankCustomer bankCustomer;
		
		Bank bank = bankService.findBankById(bankId);
		
		for (Customer customer : bank.getCustomers()) {
			
			bankCustomer = new BankCustomer(customer);
			bankCustomers.add(bankCustomer);
		}
		
		return bankCustomers;
	}
	
	/* =================== implementing retrieveCustomerById() ===================  */

	public BankCustomer retrieveCustomerById(Long bankId, Long customerId) {

		Bank bank = bankService.findBankById(bankId);
		Customer customer = findCustomerById(customerId, bankId);
		
		BankCustomer bankCustomer = new BankCustomer(customer);
		
		return bankCustomer;
	}

}
