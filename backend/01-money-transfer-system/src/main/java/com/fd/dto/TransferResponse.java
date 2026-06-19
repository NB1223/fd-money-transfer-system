package com.fd.dto;

import java.time.LocalDateTime;

import com.fd.model.TransactionLog;

public class TransferResponse {

    private long transactionId;
    private long fromAccountId;
    private long toAccountId;
    private double transferAmount;
    private LocalDateTime timestamp;
    private Integer pointsEarned;

    public TransferResponse(long transactionId, long fromAccountId, long toAccountId, double transferAmount, LocalDateTime time, Integer pointsEarned) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.transferAmount = transferAmount;
        this.timestamp = time; 
        this.pointsEarned = pointsEarned;
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

    public Integer getPointsEarned(){
        return pointsEarned;
    }

    public static TransferResponse fromEntityToDTO(TransactionLog transactionLog, Integer pointsEarned) {
        if (transactionLog == null) {
            return null;
        }

        return new TransferResponse(
            transactionLog.getTransactionId(),
        		transactionLog.getFromAccountId(),
        		transactionLog.getToAccountId(),
        		transactionLog.getAmount(),
                transactionLog.getCreatedOn(),
                pointsEarned

        );
    }

    public static TransferResponse fromEntityToDTO(TransactionLog transactionLog){
        return fromEntityToDTO(transactionLog, null);
    }
}
