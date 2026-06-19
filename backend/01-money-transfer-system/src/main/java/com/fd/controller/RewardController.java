package com.fd.controller;

import com.fd.dto.RewardResponse;
import com.fd.model.Reward;
import com.fd.service.IRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rewards")
public class RewardController {
    @Autowired
    private IRewardService rewardService;

    //getting all rewards for a specific account ID 
    @GetMapping("/account/{accountId}")
    public List<RewardResponse> getRewardsByAccountID(@PathVariable Long accountId){
        List<Reward> rewards = rewardService.getRewardsByAccountId(accountId);
        return rewards.stream().map(RewardResponse::fromEntityToDTO).collect(Collectors.toList());
    }
}
