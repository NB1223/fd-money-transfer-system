package com.fd.dto;

import com.fd.model.Account;

public class AccountDTO {
	
	private String holderName;
	private double balance;
	
	public AccountDTO(String holderName, double balance) {
		super();
		this.holderName = holderName;
		this.balance = balance;
	}

	public String getHolderName() {
		return holderName;
	}

	public double getBalance() {
		return balance;
	}
	
    public static Account fromDTOToEntity(AccountDTO dto) {
        if (dto == null) {
            return null;
        }
        Account account = new Account(dto.getHolderName(),dto.getBalance());
        return account;
    }
    
    public static AccountDTO fromEntityToDTO(Account account) {
        if (account == null) {
            return null;
        }
        return new AccountDTO(
        		account.getHolderName(),
        		account.getBalance()
        );
    }
	
	
	

}
