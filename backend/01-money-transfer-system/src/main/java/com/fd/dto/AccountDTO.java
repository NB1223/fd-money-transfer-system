package com.fd.dto;

import com.fd.model.Account;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccountDTO {
	
	@NotBlank(message = "Account name must not be blank") //Rejects null + empty + spaces
    @Pattern(
            regexp = "^[A-Za-z ]{3,50}$",
            message = "Account name must be 3–50 characters and contain only letters"
    )
	private String holderName;
	@NotNull(message = "Balance is required")
    @Min(value = 1, message = "Balance must be greater than 0")
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
