package com.fd.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="transactionlog")
public class TransactionLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long transactionId;
	@Column(name="fromAccountId", nullable=false)
	private long fromAccountId;
	@Column(name="toAccountId", nullable=false)
	private long toAccountId;
	@Column(name="amount", nullable=false)
	private double amount;
	@Column(name="status", nullable=true)
	private TransactionStatus status;
	@Column(name="failureReason", nullable=true)
	private String failureReason;
	@Column(name="idempotencyKey", nullable=false, unique=true)
	private String idempotencyKey;
	@Column(name="createdOn", nullable=false)
	private LocalDateTime createdOn;
	
	public TransactionLog() {
		super();
	}
	
	public TransactionLog(long fromAccountId, long toAccountId, double amount, String idempotencyKey) {
		
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
		this.status = TransactionStatus.FAILED;
		this.failureReason = "";
		this.idempotencyKey = idempotencyKey;
		this.createdOn = LocalDateTime.now();
	}


	public TransactionStatus getStatus() {
		return status;
	}


	public void setStatus(TransactionStatus status) {
		this.status = status;
	}


	public String getFailureReason() {
		return failureReason;
	}


	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
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


	public double getAmount() {
		return amount;
	}


	public String getIdempotencyKey() {
		return idempotencyKey;
	}


	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	
}
