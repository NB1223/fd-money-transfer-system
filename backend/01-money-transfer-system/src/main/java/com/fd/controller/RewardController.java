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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;

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

    @PostMapping("/claim/{rewardId}/account/{accountId}")
    public ResponseEntity<?> claimReward(@PathVariable Long rewardId, @PathVariable Long accountId){
        Double cashback = rewardService.claimReward(rewardId, accountId);
        if (cashback == null) {
            return ResponseEntity.badRequest().body("Unable to claim reward");
        }
        return ResponseEntity.ok().body(cashback);
    }

    @GetMapping("/account/{accountId}/summary")
    public ResponseEntity<?> getRewardsSummary(@PathVariable Long accountId){
        List<Reward> rewards = rewardService.getRewardsByAccountId(accountId);
        int total = rewards.stream().mapToInt(Reward::getPointsEarned).sum();
        int claimed = rewards.stream().filter(Reward::isClaimed).mapToInt(Reward::getPointsEarned).sum();
        int claimable = total - claimed;
        return ResponseEntity.ok().body(java.util.Map.of(
            "totalPoints", total,
            "claimedPoints", claimed,
            "claimablePoints", claimable
        ));
    }

    @PostMapping("/account/{accountId}/claimAll")
    public ResponseEntity<?> claimAllRewards(@PathVariable Long accountId){
        Double cashback = rewardService.claimAllRewards(accountId);
        if (cashback == null) return ResponseEntity.badRequest().body("Unable to claim rewards");
        return ResponseEntity.ok().body(cashback);
    }
}
