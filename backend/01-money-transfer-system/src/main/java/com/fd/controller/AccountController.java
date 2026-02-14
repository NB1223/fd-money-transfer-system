package com.fd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fd.dto.AccountDTO;
import com.fd.dto.AccountResponse;
import com.fd.dto.TransferResponse;
import com.fd.exception.AccountNotFoundException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;
import com.fd.service.AccountService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
	
	@Autowired
	AccountService accountService;	

	// for testing purposes only
	// http://localhost:9090/api/v1/account/all 
	@GetMapping("admin/account/all")
	public List<Account> getAllAccounts(){
		return accountService.findAllAccounts();
	}
	
	// http://localhost:9090/api/v1/accounts/create
	// @Transactional
	// @PostMapping("accounts/create")
	// public Account createAccount(@RequestBody AccountDTO account) {
	// 	return accountService.createAccount(account);
	// }
	
	// http://localhost:9090/api/v1/account/1/balance
	@GetMapping("account/{id}/balance")
	public double getBalanceById(@PathVariable long id) throws AccountNotFoundException {
		return accountService.findBalanceById(id);
	}
	
	// http://localhost:9090/api/v1/account/1/transactions
	@GetMapping("account/{id}/transactions")
	public List<TransactionLog> getAllTransactions(@PathVariable long id) throws AccountNotFoundException{
		return accountService.findAllTransactions(id);
	}
	
	// http://localhost:9090/api/v1/account/transactions/1
	@GetMapping("account/transactions/{id}")
	public Page<TransferResponse> getTransactionsByPage(@PathVariable long id, Pageable pageable) throws AccountNotFoundException{
		System.out.println("CONTROLLER HIT");
		return accountService.getTransactionsByPage(id, pageable);
	}
	
	// http://localhost:9090/api/v1/account/1
	@GetMapping("account/{id}")
	public AccountResponse getAccountDetailsById(@PathVariable long id) throws AccountNotFoundException {
		return accountService.findAccountDetailsById(id);
	}

	// http://localhost:9090/api/v1/admin/account/create
	@Transactional
	@PostMapping("admin/account/create")
	public Account createAccount(@RequestBody AccountDTO account) {
		return accountService.createAccount(account);
	}

}
