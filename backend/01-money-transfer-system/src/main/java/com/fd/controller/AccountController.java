package com.fd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fd.dto.AccountDTO;
import com.fd.dto.AccountResponse;
import com.fd.exception.AccountNotFoundException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;
import com.fd.service.AccountService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
	
	@Autowired
	AccountService accountService;	

	// for testing purposes only
	// http://localhost:9090/api/v1/accounts/all 
	@GetMapping("/all")
	public List<Account> getAllAccounts(){
		return accountService.findAllAccounts();
	}
	
	// http://localhost:9090/api/v1/accounts/create
	@Transactional
	@PostMapping("/create")
	public Account createAccount(@RequestBody AccountDTO account) {
		return accountService.createAccount(account);
	}
	
	// http://localhost:9090/api/v1/accounts/1/balance
	@GetMapping("/{id}/balance")
	public double getBalanceById(@PathVariable long id) throws AccountNotFoundException {
		return accountService.findBalanceById(id);
	}
	
	// http://localhost:9090/api/v1/accounts/1/transactions
	@GetMapping("/{id}/transactions")
	public List<TransactionLog> getAllTransactions(@PathVariable long id) throws AccountNotFoundException{
		return accountService.findAllTransactions(id);
	}
	
	// http://localhost:9090/api/v1/accounts/1
	@GetMapping("/{id}")
	public AccountResponse getAccountDetailsById(@PathVariable long id) throws AccountNotFoundException {
		return accountService.findAccountDetailsById(id);
	}

}
