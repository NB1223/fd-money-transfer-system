package com.fd.dto;

import com.fd.model.TransactionLog;

public class TransactionLogDTO {
	
	private Long fromAccountId;
	private Long toAccountId;
	private double amount;
	private String idempotencyKey;
	
	public TransactionLogDTO(Long fromAccountId, Long toAccountId, double amount, String idempotencyKey) {
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
	
    public static TransactionLog fromDTOToEntity(TransactionLogDTO dto) {
        if (dto == null) {
            return null;
        }
        TransactionLog transactionLog = new TransactionLog(dto.getFromAccountId()
        		,dto.getToAccountId(),dto.getAmount(),dto.getIdempotencyKey());
        return transactionLog;
    }
    
    public static TransactionLogDTO fromEntityToDTO(TransactionLog transactionLog) {
        if (transactionLog == null) {
            return null;
        }
        return new TransactionLogDTO(
        		transactionLog.getFromAccountId(),
        		transactionLog.getToAccountId(),
        		transactionLog.getAmount(),
        		transactionLog.getIdempotencyKey()
        );
    }
	
	

}
