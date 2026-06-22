import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterModule } from '@angular/router';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    RouterModule
  ],
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

  recentTransaction: any = null;
  recentTransactionLoading: boolean = false;

  incomeTotal: number = 0;
  expenseTotal: number = 0;

  partnerFrequency: { [key: string]: number } = {};

  tips: string[] = [
    'Track your spending weekly to build smarter habits.',
    'Aim to save at least 20% of your monthly income.',
    'Diversify investments to reduce financial risk.',
    'Emergency funds should cover 3–6 months of expenses.'
  ];

  rewardPoints: number = 0;
  copiedCode: string = '';
 
  milestones = [
    { points: 150, code: 'MTS-BRZ-K9X4', label: 'Bronze',  tier: 'bronze' },
    { points: 300, code: 'MTS-SLV-TM2P', label: 'Silver',  tier: 'silver' },
    { points: 500, code: 'MTS-GLD-ZQ7R', label: 'Gold',    tier: 'gold'   },
  ];
 
  get progressPercent(): number {
    const maxPoints = this.milestones[this.milestones.length - 1].points;
    return Math.min((this.rewardPoints / maxPoints) * 100, 100);
  }

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {

    console.log('DASHBOARD HIT');

    this.accountId = Number(sessionStorage.getItem('accountId'));
    this.elongatedId = Number(sessionStorage.getItem('elongatedId'));
    this.userName = sessionStorage.getItem('username');

    this.fetchAccountBalance(this.accountId);
    this.fetchRecentTransaction(this.accountId);
    this.fetchRewardPoints(this.accountId);


    this.setGreeting();

    this.currentDate = new Date().toLocaleDateString();

    this.updateTime();
    setInterval(() => this.updateTime(), 1000);

    this.wealthTip =
      this.tips[Math.floor(Math.random() * this.tips.length)];
  }

  updateTime(): void {
    const now = new Date();
    this.currentTime = now.toLocaleTimeString();
  }

  fetchRewardPoints(accountId: number): void {
    this.accountService.getRewardPoints(accountId).subscribe({
      next:  (data) => { this.rewardPoints = Number(data); },
      error: (err)  => { console.error('Error fetching reward points:', err); }
    });
  }
 
  copyCode(code: string): void {
    navigator.clipboard.writeText(code).then(() => {
      this.copiedCode = code;
      setTimeout(() => { this.copiedCode = ''; }, 2000);
    });
  }

  setGreeting(): void {

    const hour = new Date().getHours();

    if (hour < 12) {
      this.greeting = 'Good Morning';
    }
    else if (hour < 18) {
      this.greeting = 'Good Afternoon';
    }
    else {
      this.greeting = 'Good Evening';
    }
  }

  fetchAccountBalance(accountId: number): void {

    this.accountService.getAccountBalance(accountId)
      .subscribe({

        next: (data) => {

          this.accountBalance = Number(data);

          console.log(
            'Balance fetched:',
            this.accountBalance
          );
        },

        error: (err) => {
          console.error(
            'Error fetching account details:',
            err
          );
        }
      });
  }

  fetchRecentTransaction(accountId: number): void {

    this.recentTransactionLoading = true;

    this.accountService
      .getTransactionsByPage(accountId, 0, 1)
      .subscribe({

        next: (data) => {

          if (
            data.content &&
            data.content.length > 0
          ) {
            this.recentTransaction =
              data.content[0];
          }

          this.recentTransactionLoading = false;
        },

        error: (err) => {

          console.error(
            'Error fetching recent transaction:',
            err
          );

          this.recentTransactionLoading = false;
        }
      });
  }

}