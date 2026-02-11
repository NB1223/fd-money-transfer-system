package com.fd.dto;

import com.fd.model.TransactionLog;

public class TransferResponse {

    private long transactionId;
    private long fromAccountId;
    private long toAccountId;
    private double newBalance;
    private double transferAmount;

    public TransferResponse(long transactionId, long fromAccountId, long toAccountId, double newBalance, double transferAmount) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.newBalance = newBalance;
        this.transferAmount = transferAmount;
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

    public double getNewBalance() {
        return newBalance;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public static TransferResponse fromEntityToDTO(TransactionLog transactionLog, double newBalance) {
        if (transactionLog == null) {
            return null;
        }

        return new TransferResponse(
            transactionLog.getTransactionId(),
        		transactionLog.getFromAccountId(),
        		transactionLog.getToAccountId(),
                newBalance,
        		transactionLog.getAmount()

        );
    }
}
