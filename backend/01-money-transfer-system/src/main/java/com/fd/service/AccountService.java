package com.fd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fd.dto.AccountDTO;
import com.fd.dto.AccountResponse;
import com.fd.dto.TransferRequest;
import com.fd.dto.TransferResponse;
import com.fd.exception.AccountNotFoundException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;
import com.fd.repository.IAccountRepository;
import com.fd.repository.ITransactionLogRepository;

@Service
public class AccountService implements IAccountService{
	
	@Autowired
	IAccountRepository accountRepo;
	@Autowired
	ITransactionLogRepository transactionLogRepo;
	
	@Override
	public Account createAccount(AccountDTO accountDTO) {
		
		return accountRepo.save(AccountDTO.fromDTOToEntity(accountDTO));
		
	}
	
	@Override
	public double findBalanceById(Long accountId) throws AccountNotFoundException {
		Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("User Account not found."));

		return account.getBalance();
	}
	
	@Override
	public List<TransactionLog> findAllTransactions(long id) throws AccountNotFoundException {
		if(accountRepo.findById(id).isEmpty()) {
			throw new AccountNotFoundException("User Account not found.");
		}else {			
			return transactionLogRepo.findTransactionsByAccountId(id);
		}
	}

	@Override
	public AccountResponse findAccountDetailsById(long id) throws AccountNotFoundException {
		Account account = accountRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("User Account not found."));
						return AccountResponse.fromEntityToDTO(account);
	}
    public List<Account> findAllAccounts() {
		return accountRepo.findAll();
    }
    
    @Override
	public Page<TransferResponse> getTransactionsByPage(long fromAccountID, Pageable pageable) {
		return transactionLogRepo
				.getTransactionsByAccountId(fromAccountID, pageable)
                .map(TransferResponse::fromEntityToDTO);
	}


}
