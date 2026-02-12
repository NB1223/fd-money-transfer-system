package com.fd.dto;

import java.time.LocalDateTime;

import com.fd.model.TransactionLog;

public class TransferResponse {

    private long transactionId;
    private long fromAccountId;
    private long toAccountId;
    private double transferAmount;
    private LocalDateTime timestamp;

    public TransferResponse(long transactionId, long fromAccountId, long toAccountId, double transferAmount, LocalDateTime time) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.transferAmount = transferAmount;
        this.timestamp = time; 
    }

    public long getTransactionId() {
        return transactionId;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public LocalDateTime getTimestamp() { 
        return timestamp; 
    }   

    public static TransferResponse fromEntityToDTO(TransactionLog transactionLog) {
        if (transactionLog == null) {
            return null;
        }

        return new TransferResponse(
            transactionLog.getTransactionId(),
        		transactionLog.getFromAccountId(),
        		transactionLog.getToAccountId(),
        		transactionLog.getAmount(),
                transactionLog.getCreatedOn()

        );
    }
}
