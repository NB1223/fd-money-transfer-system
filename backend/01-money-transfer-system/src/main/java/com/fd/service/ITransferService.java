package com.fd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fd.dto.TransactionLogDTO;
import com.fd.exception.AccountNotActiveException;
import com.fd.exception.AccountNotFoundException;
import com.fd.exception.InsuffiecientBalanceException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;

public interface ITransferService {
	
	public List<TransactionLog> findAllTransactions();
	List<Account> transactionValidation(TransactionLogDTO transactionLogDTO) 
			throws AccountNotFoundException, InsuffiecientBalanceException, AccountNotActiveException;
	TransactionLog executeTransaction(TransactionLogDTO transactionLogDTO, List<Account> accounts);
	Page<TransactionLogDTO> getTransactionsByPage(long fromAccountID, Pageable pageable);
	public Page<TransactionLogDTO> findAllTransactionsByPage(Pageable pageable);
	
}
