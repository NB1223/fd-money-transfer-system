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
    showScratchModal: boolean = false;
    scratchedAmount: number | null = null;
    revealing: boolean = false;
    claimablePoints: number = 0;
    claimedPoints: number = 0;
    // pagination
    currentPage: number = 0; // zero-based
    pageSize: number = 5;
    totalPages: number = 0;
    displayedRewards: RewardResponse[] = [];

    constructor(private rewardService: RewardService){ }

    ngOnInit(): void{

        //to get the logged in user's account Id from sessionStorage
        const accountIdStr = sessionStorage.getItem('accountId');
        if (accountIdStr) {
            const accountId = parseInt(accountIdStr, 10);
            this.loadRewards(accountId);
            this.loadSummary(accountId);
        }
    }

    loadRewards(accountId: number): void {
        this.rewardService.getRewardsByAccountId(accountId).subscribe({
            next: (data) => {
                this.rewards = data;
                this.totalPoints = this.rewards.reduce((sum, reward) => sum+reward.pointsEarned, 0);
                this.claimedPoints = this.rewards.filter(r => r.claimed).reduce((s,r) => s + r.pointsEarned, 0);
                this.claimablePoints = this.totalPoints - this.claimedPoints;
                // pagination setup
                this.currentPage = 0;
                this.totalPages = Math.max(1, Math.ceil(this.rewards.length / this.pageSize));
                this.updateDisplayedRewards();
            },
            error: (err) => {
                console.error('Error loading rewards:', err);
            }
        });
    }

    loadSummary(accountId: number): void {
        this.rewardService.getRewardsSummary(accountId).subscribe({
            next: (s) => {
                this.totalPoints = s.totalPoints;
                this.claimedPoints = s.claimedPoints;
                this.claimablePoints = s.claimablePoints;
            }, error: (err) => console.error('Error loading summary', err)
        })
    }

    claimAll(): void {
        const accountIdStr = sessionStorage.getItem('accountId');
        if (!accountIdStr) return;
        const accountId = parseInt(accountIdStr, 10);
        if (this.claimablePoints <= 0) return;
        this.showScratchModal = true;
        this.revealing = false;
        this.rewardService.claimAllRewards(accountId).subscribe({
            next: (amount) => { this.scratchedAmount = amount; this.loadSummary(accountId); this.loadRewards(accountId); },
            error: (err) => { console.error('Error claiming all rewards', err); this.showScratchModal = false; }
        });
    }

    revealScratch(): void { this.revealing = true; }

    closeModal(): void {
        this.showScratchModal = false;
        this.scratchedAmount = null;
        const accountIdStr = sessionStorage.getItem('accountId');
        if (accountIdStr) { const accountId = parseInt(accountIdStr, 10); this.loadSummary(accountId); this.loadRewards(accountId); }
    }

    updateDisplayedRewards(): void {
        const start = this.currentPage * this.pageSize;
        this.displayedRewards = this.rewards.slice(start, start + this.pageSize);
    }

    goToPage(page: number): void {
        if (page < 0 || page >= this.totalPages) return;
        this.currentPage = page;
        this.updateDisplayedRewards();
    }
}
