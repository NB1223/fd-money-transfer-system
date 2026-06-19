package com.fd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fd.model.Reward;
import com.fd.model.TransactionLog;

public interface IRewardService {
	
	//to check eligibility, calculate points and saving rewards if eligible
    Reward calculateAndSaveReward(TransactionLog successfulTransaction, Long senderAccountId);

    //to get all rewards for a specific account
    List<Reward> getRewardsByAccountId(Long accountId);
}
