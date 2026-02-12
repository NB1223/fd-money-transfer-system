package com.fd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fd.dto.AccountDTO;
import com.fd.dto.AccountResponse;
import com.fd.dto.TransferResponse;
import com.fd.exception.AccountNotFoundException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;

public interface IAccountService {
	Account createAccount(AccountDTO accountDTO);
	double findBalanceById(Long accountId) throws AccountNotFoundException;
	List<TransactionLog> findAllTransactions(long id) throws AccountNotFoundException;
	AccountResponse findAccountDetailsById(long id) throws AccountNotFoundException;
	Page<TransferResponse> getTransactionsByPage(long fromAccountID, Pageable pageable);


}
