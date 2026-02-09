package com.fd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fd.dto.TransferRequest;
import com.fd.exception.AccountNotActiveException;
import com.fd.exception.AccountNotFoundException;
import com.fd.exception.InsuffiecientBalanceException;
import com.fd.exception.SelfTransferException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;
import com.fd.model.TransactionStatus;
import com.fd.repository.IAccountRepository;
import com.fd.repository.ITransactionLogRepository;

@Service
public class TransferService implements ITransferService {
	
	@Autowired
	ITransactionLogRepository transactionLogRepo;
	@Autowired
	IAccountRepository accountRepo;

	public List<TransactionLog> findAllTransactions(){
		return transactionLogRepo.findAll();
	}
	
	//TODO:Implement Duplicatetransfer exception - currently handled by model 
	

	@Override
	public List<Account> transactionValidation(TransferRequest transferRequest) 
			throws AccountNotFoundException, InsuffiecientBalanceException, AccountNotActiveException, SelfTransferException {
		
		Account fromAccount = accountRepo.findById(transferRequest.getFromAccountId())
				.orElseThrow(() -> new AccountNotFoundException("User Account Not Found"));
		
		Account toAccount = accountRepo.findById(transferRequest.getToAccountId())
				.orElseThrow(() -> new AccountNotFoundException("Receiver Account not found"));
		
		if(fromAccount.getBalance() < transferRequest.getAmount()) {
			throw new InsuffiecientBalanceException("Insufficient Funds");
		}

		if (transferRequest.getFromAccountId() == transferRequest.getToAccountId()){
			throw new SelfTransferException("Self transfer not allowed.");
		}
		
		if(!toAccount.isActive() || !fromAccount.isActive()) {
			if(toAccount.isActive()==false) {
				throw new AccountNotActiveException("Receiver Account is not active.");
			}else {
				throw new AccountNotActiveException("User Account is not active.");
			}
		}
		
		List<Account> accounts = new ArrayList<>();
		accounts.add(fromAccount);
		accounts.add(toAccount);
		return accounts;
	}
	
	@Override
	public TransactionLog executeTransaction(TransferRequest transferRequest, List<Account> accounts) {
						
		Account fromAccount = accounts.get(0);
		Account toAccount = accounts.get(1);
		//Debit money from user
		fromAccount.debit(transferRequest.getAmount());
		//Credit money to receiver
		toAccount.credit(transferRequest.getAmount());
		TransactionLog transactionLog = TransferRequest.fromDTOToEntity(transferRequest);
		transactionLog.setStatus(TransactionStatus.SUCCESS);
			
		return transactionLogRepo.save(transactionLog);

    
	}

}
