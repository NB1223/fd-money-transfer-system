package com.fd.dto;

import java.time.LocalDateTime;

import com.fd.model.TransactionLog;

public class TransferResponse {

    private long transactionId;
    private long fromAccountId;
    private long toAccountId;
    private double transferAmount;
    private LocalDateTime timestamp;
    private String remarks;
    private String toHolderName;
    private int rewardPointsEarned;

    public TransferResponse(long transactionId, long fromAccountId, long toAccountId, double transferAmount, LocalDateTime time) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.transferAmount = transferAmount;
        this.timestamp = time;
        this.remarks = "";
        this.toHolderName = "";
        this.rewardPointsEarned = 0;
    }

    public TransferResponse(long transactionId, long fromAccountId, long toAccountId, double transferAmount, LocalDateTime time, String remarks, String toHolderName, int rewardPointsEarned) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.transferAmount = transferAmount;
        this.timestamp = time;
        this.remarks = remarks;
        this.toHolderName = toHolderName;
        this.rewardPointsEarned = rewardPointsEarned;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getToHolderName() {
        return toHolderName;
    }

    public void setToHolderName(String toHolderName) {
        this.toHolderName = toHolderName;
    }

    public int getRewardPointsEarned() {
        return rewardPointsEarned;
    }

    public void setRewardPointsEarned(int rewardPointsEarned) {
        this.rewardPointsEarned = rewardPointsEarned;
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
                transactionLog.getCreatedOn(),
                transactionLog.getRemarks(),
                transactionLog.getToHolderName(),
                transactionLog.getRewardPointsEarned()
        );
    }
}
