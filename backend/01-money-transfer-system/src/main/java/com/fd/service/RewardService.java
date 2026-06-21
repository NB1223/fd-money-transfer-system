package com.fd.service;

import com.fd.model.Reward;
import com.fd.model.TransactionLog;
import com.fd.model.TransactionStatus;
import com.fd.repository.IRewardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.stereotype.Service;
import com.fd.repository.IAccountRepository;
import com.fd.repository.ITransactionLogRepository;
import com.fd.model.Account;
import java.util.Optional;
import java.util.Random;
import com.fd.model.TransactionLog;
import com.fd.model.TransactionStatus;

@Service
public class RewardService implements IRewardService {
	
	@Autowired
	private IRewardRepository rewardRepository;
    
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ITransactionLogRepository transactionLogRepo;
	
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

        // reward for transactions of 100.0 or more
        if (transaction.getAmount() < 100.0){
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

    @Override
    public Double claimReward(Long rewardId, Long accountId) {
        Optional<Reward> opt = rewardRepository.findById(rewardId);
        if (!opt.isPresent()) return null;
        Reward reward = opt.get();
        if (!reward.getAccountId().equals(accountId)) return null;
        if (reward.isClaimed()) return null;

        int points = reward.getPointsEarned();
        int cashback = 0;
        Random rnd = new Random();
        if (points >= 100) {
            cashback = rnd.nextInt(11) + 40; //40-50
        } else if (points >= 75) {
            cashback = rnd.nextInt(16) + 25; //25-40
        } else if (points >= 50) {
            cashback = rnd.nextInt(11) + 15; //15-25
        } else if (points >= 20) {
            cashback = rnd.nextInt(6) + 5; //5-10
        } else if (points > 0) {
            cashback = rnd.nextInt(5) + 1; //1-5
        } else {
            cashback = 0;
        }

        // credit account
        Optional<Account> acctOpt = accountRepository.findById(accountId);
        if (!acctOpt.isPresent()) return null;
        Account account = acctOpt.get();
        account.credit(cashback);
        accountRepository.save(account);

        // create transaction log from bank (use 0 as bank account id)
        long BANK_ACCOUNT_ID = 0L;
        String idempotencyKey = "reward-" + rewardId + "-" + System.currentTimeMillis();
        TransactionLog t = new TransactionLog(BANK_ACCOUNT_ID, accountId, (double)cashback, idempotencyKey);
        t.setStatus(TransactionStatus.SUCCESS);
        transactionLogRepo.save(t);

        reward.setClaimed(true);
        rewardRepository.save(reward);

        return (double) cashback;
    }

    @Override
    public Double claimAllRewards(Long accountId) {
        List<Reward> rewards = rewardRepository.findByAccountId(accountId);
        List<Reward> unclaimed = rewards.stream().filter(r -> !r.isClaimed()).toList();
        if (unclaimed.isEmpty()) return 0.0;

        int totalPoints = unclaimed.stream().mapToInt(Reward::getPointsEarned).sum();

        int cashback = 0;
        Random rnd = new Random();
        if (totalPoints >= 100) {
            cashback = rnd.nextInt(11) + 40; //40-50
        } else if (totalPoints >= 75) {
            cashback = rnd.nextInt(16) + 25; //25-40
        } else if (totalPoints >= 50) {
            cashback = rnd.nextInt(11) + 15; //15-25
        } else if (totalPoints >= 20) {
            cashback = rnd.nextInt(6) + 5; //5-10
        } else if (totalPoints > 0) {
            cashback = rnd.nextInt(5) + 1; //1-5
        }

        // credit account
        Optional<Account> acctOpt = accountRepository.findById(accountId);
        if (!acctOpt.isPresent()) return null;
        Account account = acctOpt.get();
        account.credit(cashback);
        accountRepository.save(account);

        // create transaction log from bank
        long BANK_ACCOUNT_ID = 0L;
        String idempotencyKey = "reward-all-" + accountId + "-" + System.currentTimeMillis();
        TransactionLog t = new TransactionLog(BANK_ACCOUNT_ID, accountId, (double)cashback, idempotencyKey);
        t.setStatus(TransactionStatus.SUCCESS);
        transactionLogRepo.save(t);

        // mark all unclaimed as claimed
        for (Reward r : unclaimed) {
            r.setClaimed(true);
        }
        rewardRepository.saveAll(unclaimed);

        return (double) cashback;
    }

}