package com.fd.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="account")
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long accountId;

	@Column(name="holderName", nullable=false)
	private String holderName;
	
	@Column(name="balance", nullable=false)
	private double balance;

	@Column(name="accountStatus", nullable=false)
	private AccountStatus accountStatus;

	@Column(name="version", nullable=false)
	private int version;

	@Column(name="lastUpdated", nullable=false)
	private LocalDateTime lastUpdated;

	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "user_id", nullable = true, unique = true)
	private UserEntity user;
	
	public Account() {
		super();
		
	}
		
	public Account(String holderName, double balance) {
		this.accountId = 0;
		this.holderName = holderName;
		this.balance = balance;
		this.accountStatus = AccountStatus.LOCKED;
		this.version = 0;
		this.lastUpdated = LocalDateTime.now();
		
	}
	
	public long getAccountId() {
		return this.accountId;
	}
	
	public String getHolderName() {
		return this.holderName;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void setBalance(double newBalance) {
		this.balance = newBalance;
	}
	
	public AccountStatus getAccountStatus() {
		return this.accountStatus;
	}
	
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public double getVersion() {
		return this.version;
	}
	
	public void setVersion(int newVersion) {
		this.version = newVersion;
	}
	
	public LocalDateTime getLastUpdated() {
		return this.lastUpdated;
	}
	
	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public void debit(double amount) {
		this.balance -= amount;
	}
	
	public void credit(double amount) {
		this.balance += amount;
	}
	
	public boolean isActive() {
		return accountStatus == AccountStatus.ACTIVE;
	}

	public UserEntity getUser() {
		return user;
	}

    public void setUser(UserEntity user) {
        this.user = user;
    }
	
}
