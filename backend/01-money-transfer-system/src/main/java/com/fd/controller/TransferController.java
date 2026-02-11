package com.fd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fd.dto.TransferRequest;
import com.fd.dto.TransferResponse;
import com.fd.exception.AccountNotActiveException;
import com.fd.exception.AccountNotFoundException;
import com.fd.exception.InsuffiecientBalanceException;
import com.fd.exception.SelfTransferException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;
import com.fd.service.ITransferService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {
	
	@Autowired
	ITransferService transferService;
	
	// http://localhost:9090/api/v1/transfers
	@Transactional
	@PostMapping
	public ResponseEntity<TransferResponse> transferFund(@RequestBody TransferRequest transferRequest)
			throws AccountNotActiveException, InsuffiecientBalanceException, AccountNotFoundException, SelfTransferException {
		List<Account> transactionAccounts = transferService.transactionValidation(transferRequest);
		return ResponseEntity.ok(transferService.executeTransaction(transferRequest,transactionAccounts));
	}
	
	// http://localhost:9090/api/v1/transfers/transactions
	@GetMapping("/transactions")
	public List<TransactionLog> getAllTransactions() {
		return transferService.findAllTransactions();
	}
	
	// http://localhost:9090/api/v1/transfers/alltransactions
	@GetMapping("/alltransactions")
	public Page<TransferRequest> getAllTransactionsByPage(Pageable pageable) {
		return transferService.findAllTransactionsByPage(pageable);
	}

}
