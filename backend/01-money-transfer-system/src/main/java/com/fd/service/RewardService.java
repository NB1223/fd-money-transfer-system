package com.fd.service;

import com.fd.model.Reward;
import com.fd.model.TransactionLog;
import com.fd.model.TransactionStatus;
import com.fd.repository.IRewardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RewardService implements IRewardService {
	
	@Autowired
	private IRewardRepository rewardRepository;
	
    @Override
    public Reward calculateAndSaveReward(TransactionLog successfulTransaction, Long senderAccountId){

        if (!isEligibleForReward(successfulTransaction, senderAccountId)){
            return null;
        }

        int pointsEarned = (int) Math.floor(successfulTransaction.getAmount() / 100.0);

        Reward reward = new Reward(successfulTransaction.getTransactionId(), senderAccountId, pointsEarned);
        return rewardRepository.save(reward);
    }

    @Override
    public List<Reward> getRewardsByAccountId(Long accountId){
        return rewardRepository.findByAccountId(accountId);
    }

    private boolean isEligibleForReward(TransactionLog transaction, Long senderAccountId){
        if (transaction.getStatus() != TransactionStatus.SUCCESS){
            return false;
        }

        if (transaction.getAmount() <= 100.0){
            return false;
        }

        if (transaction.getFromAccountId() == transaction.getToAccountId()) {
            return false;
        }
        
        if (rewardRepository.existsByTransactionId(transaction.getTransactionId())){
            return false;
        }

        return true;
    }

}