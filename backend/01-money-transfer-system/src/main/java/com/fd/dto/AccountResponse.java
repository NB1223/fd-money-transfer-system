package com.fd.dto;

import com.fd.model.Account;
import com.fd.model.AccountStatus;

public class AccountResponse {
    
    private long accountId;
	private String holderName;
	private double balance;
	private AccountStatus accountStatus;

    public AccountResponse(long accountId, String holderName, double balance, AccountStatus accountStatus) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = balance;
        this.accountStatus = accountStatus;
    }


    public long getAccountId() {
        return accountId;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public static AccountResponse fromEntityToDTO(Account account) {
        if (account == null) {
            return null;
        }
        return new AccountResponse(
        		account.getAccountId(),
        		account.getHolderName(),
        		account.getBalance(),
        		account.getAccountStatus()
        );
    }
        		
}
    


