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
	private String remarks;
	
	public TransferRequest() {
		super();
		this.remarks = "";
	}

	public TransferRequest(Long fromAccountId, Long toAccountId, double amount, String idempotencyKey) {
		super();
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
		this.idempotencyKey = idempotencyKey;
		this.remarks = "";
	}

	public TransferRequest(Long fromAccountId, Long toAccountId, double amount, String idempotencyKey, String remarks) {
		super();
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
		this.idempotencyKey = idempotencyKey;
		this.remarks = remarks;
	}

	public Long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(long toAccountId) {
		this.toAccountId = toAccountId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getIdempotencyKey() {
		return idempotencyKey;
	}

	public void setIdempotencyKey(String idempotencyKey) {
		this.idempotencyKey = idempotencyKey;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
    public static TransactionLog fromDTOToEntity(TransferRequest dto) {
        if (dto == null) {
            return null;
        }
        TransactionLog transactionLog = new TransactionLog(dto.getFromAccountId()
        		,dto.getToAccountId(),dto.getAmount(),dto.getIdempotencyKey());
        transactionLog.setRemarks(dto.getRemarks());
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
