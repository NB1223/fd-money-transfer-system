package com.fd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fd.dto.TransferRequest;
import com.fd.dto.TransferResponse;
import com.fd.exception.AccountNotActiveException;
import com.fd.exception.AccountNotFoundException;
import com.fd.exception.InsuffiecientBalanceException;
import com.fd.exception.SelfTransferException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;

public interface ITransferService {
	
	public List<TransactionLog> findAllTransactions();
	
	List<Account> transactionValidation(TransferRequest transferRequest) 
			throws AccountNotFoundException, InsuffiecientBalanceException, AccountNotActiveException, SelfTransferException;
	TransferResponse executeTransaction(TransferRequest transferRequest, List<Account> accounts);

	public Page<TransferRequest> findAllTransactionsByPage(Pageable pageable);
	
	
}
