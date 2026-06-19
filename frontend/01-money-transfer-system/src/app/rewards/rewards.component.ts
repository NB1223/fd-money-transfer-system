import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RewardService, RewardResponse } from '../reward.service';

@Component({
    selector: 'app-rewards',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './rewards.component.html',
    styleUrls: ['./rewards.component.css']
})

export class RewardsComponent implements OnInit{
    rewards: RewardResponse[] = [];
    totalPoints: number = 0;

    constructor(private rewardService: RewardService){ }

    ngOnInit(): void{

        //to get the logged in user's account Id from sessionStorage
        const accountIdStr = sessionStorage.getItem('accountId');
        if (accountIdStr) {
            const accountId = parseInt(accountIdStr, 10);
            this.loadRewards(accountId);
        }
    }

    loadRewards(accountId: number): void {
        this.rewardService.getRewardsByAccountId(accountId).subscribe({
            next: (data) => {
                this.rewards = data;
                this.totalPoints = this.rewards.reduce((sum, reward) => sum+reward.pointsEarned, 0);
            },
            error: (err) => {
                console.error('Error loading rewards:', err);
            }
        });
    }
}
