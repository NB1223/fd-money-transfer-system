import { Component } from '@angular/core';
import { AccountService } from '../account.service';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterModule } from '@angular/router';
import { RewardService } from '../reward.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  accountBalance: number = 0;
  accountId: number = 0;
  elongatedId: number = 0;
  userName: string | null = '';
  greeting: string = '';
  currentDate: string = '';
  currentTime: string = '';
  wealthTip: string = '';
  totalPoints: number = 0;

  tips: string[] = [
    "Track your spending weekly to build smarter habits.",
    "Aim to save at least 20% of your monthly income.",
    "Diversify investments to reduce financial risk.",
    "Emergency funds should cover 3–6 months of expenses."
  ];

  constructor(private accountService: AccountService, private rewardService: RewardService) {}

   ngOnInit(): void {
    console.log("DASHBOARD HIT");
    this.accountId = Number(sessionStorage.getItem('accountId')); // use accountId key
    this.elongatedId = Number(sessionStorage.getItem('elongatedId')); // use elongatedId key
    this.userName = sessionStorage.getItem('username');
    this.fetchAccountBalance(this.accountId);
    this.loadTotalPoints(this.accountId);
    this.setGreeting();
    this.currentDate = new Date().toLocaleDateString();
    this.updateTime();
    setInterval(() => this.updateTime(), 1000);
    this.wealthTip = this.tips[Math.floor(Math.random() * this.tips.length)];

  }

  updateTime() {
    const now = new Date();
    this.currentTime = now.toLocaleTimeString();
  }

  fetchAccountBalance(accountId: number): void {
    this.accountService.getAccountBalance(accountId).subscribe({
      next: (data) => {
        console.log("Data received from service:", data);
        this.accountBalance = Number(data);
        console.log("Balance fetched:", this.accountBalance);
      },
      error: (err) => {
        console.error('Error fetching account details:', err);
      }
    });
  }

  loadTotalPoints(accountId: number): void{
    this.rewardService.getRewardsByAccountId(accountId).subscribe({
      next: (rewards) => {
        this.totalPoints = rewards.reduce((sum, reward) => sum + reward.pointsEarned, 0);
      },
      error: (err) => {
        console.error('Error loading rewards', err)
      }
    });
  }

  setGreeting() {
    const hour = new Date().getHours();

    if (hour < 12) {
      this.greeting = 'Good Morning';
    } else if (hour < 18) {
      this.greeting = 'Good Afternoon';
    } else {
      this.greeting = 'Good Evening';
    }
  }


}
