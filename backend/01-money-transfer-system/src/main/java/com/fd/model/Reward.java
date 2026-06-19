package com.fd.model;

import java.math.BigInteger;
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
@Table(name="reward")
public class Reward {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long rewardId;

	@Column(name="transaction_id", nullable=false)
	private Long transactionId;
	
	@Column(name = "account_id", nullable = false)
    private Long accountId;

	@Column(name = "points_earned", nullable = false)
    private Integer pointsEarned;

	@Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
	
	public Reward() {
		super();
		
	}
		
	
    public Reward(Long transactionId, Long accountId, Integer pointsEarned){
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.pointsEarned = pointsEarned;
        this.createdAt = LocalDateTime.now();
    }

    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId){
        this.rewardId = rewardId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId){
        this.transactionId = transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId){
        this.accountId = accountId;
    }

    public Integer getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(Integer pointsEarned){
        this.pointsEarned = pointsEarned;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
    
	
}
