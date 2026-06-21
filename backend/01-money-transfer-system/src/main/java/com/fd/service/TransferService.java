package com.fd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fd.dto.TransferRequest;
import com.fd.dto.TransferResponse;
import com.fd.exception.AccountNotActiveException;
import com.fd.exception.AccountNotFoundException;
import com.fd.exception.InsuffiecientBalanceException;
import com.fd.exception.SelfTransferException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;
import com.fd.model.TransactionStatus;
import com.fd.repository.IAccountRepository;
import com.fd.repository.ITransactionLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
	public TransferResponse executeTransaction(TransferRequest transferRequest, List<Account> accounts) {
						
		try {
        Account fromAccount = accounts.get(0);
        Account toAccount = accounts.get(1);
        
        fromAccount.debit(transferRequest.getAmount());
        toAccount.credit(transferRequest.getAmount());
        
        // Calculate reward points: 1 point for every 100 transferred
        int rewardPoints = (int) (transferRequest.getAmount() / 100);
        fromAccount.addRewardPoints(rewardPoints);
        
        accountRepo.save(fromAccount);
        accountRepo.save(toAccount);
        
        TransactionLog transactionLog = TransferRequest.fromDTOToEntity(transferRequest);
        transactionLog.setStatus(TransactionStatus.SUCCESS);
        transactionLog.setToHolderName(toAccount.getHolderName());
        transactionLog.setRewardPointsEarned(rewardPoints);
        transactionLogRepo.save(transactionLog);

        return TransferResponse.fromEntityToDTO(transactionLog);
    } catch (Exception e) {
        TransactionLog failedLog = TransferRequest.fromDTOToEntity(transferRequest);
        failedLog.setStatus(TransactionStatus.FAILED);
        failedLog.setFailureReason(e.getMessage());
        transactionLogRepo.save(failedLog);
        throw e;
    }
	}
	

	@Override
	public Page<TransferRequest> findAllTransactionsByPage(Pageable pageable) {
		return transactionLogRepo
				.getAllTransactionsByPage(pageable)
                .map(TransferRequest::fromEntityToDTO);
	}
}
