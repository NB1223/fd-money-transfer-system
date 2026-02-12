package com.fd.dto;

import com.fd.model.TransactionLog;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TransferRequest {
	
	 @NotNull(message = "From Account Number is required")
    private long fromAccountId;
    @NotNull(message = "To Account Number is required")
    private long toAccountId;
    @NotNull(message = "Transfer amount is required")
    @Min(value = 1, message = "Transfer amount must be greater than 0")
	private double amount;
	private String idempotencyKey;
	
	public TransferRequest(Long fromAccountId, Long toAccountId, double amount, String idempotencyKey) {
		super();
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
		this.idempotencyKey = idempotencyKey;
	}

	public Long getFromAccountId() {
		return fromAccountId;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

	public double getAmount() {
		return amount;
	}
	
	public String getIdempotencyKey() {
		return idempotencyKey;
	}
	
    public static TransactionLog fromDTOToEntity(TransferRequest dto) {
        if (dto == null) {
            return null;
        }
        TransactionLog transactionLog = new TransactionLog(dto.getFromAccountId()
        		,dto.getToAccountId(),dto.getAmount(),dto.getIdempotencyKey());
        return transactionLog;
    }
    
    public static TransferRequest fromEntityToDTO(TransactionLog transactionLog) {
        if (transactionLog == null) {
            return null;
        }
        return new TransferRequest(
        		transactionLog.getFromAccountId(),
        		transactionLog.getToAccountId(),
        		transactionLog.getAmount(),
        		transactionLog.getIdempotencyKey()
        );
    }
	
	

}
