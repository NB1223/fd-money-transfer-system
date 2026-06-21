import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterModule } from '@angular/router';
import { AccountService } from '../account.service';

import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

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

  incomeExpenseChart: Chart | null = null;
  partnerChart: Chart | null = null;

  tips: string[] = [
    'Track your spending weekly to build smarter habits.',
    'Aim to save at least 20% of your monthly income.',
    'Diversify investments to reduce financial risk.',
    'Emergency funds should cover 3–6 months of expenses.'
  ];

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {

    console.log('DASHBOARD HIT');

    this.accountId = Number(sessionStorage.getItem('accountId'));
    this.elongatedId = Number(sessionStorage.getItem('elongatedId'));
    this.userName = sessionStorage.getItem('username');

    this.fetchAccountBalance(this.accountId);
    this.fetchRecentTransaction(this.accountId);
    this.fetchTransactionStats(this.accountId);

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

  fetchTransactionStats(accountId: number): void {

    this.accountService
      .getTransactionsByPage(accountId, 0, 1000)
      .subscribe({

        next: (data) => {

          this.incomeTotal = 0;
          this.expenseTotal = 0;

          this.partnerFrequency = {};

          data.content.forEach((tx: any) => {

            // Income
            if (tx.toAccountId === accountId) {

              this.incomeTotal +=
                tx.transferAmount;

              const partner =
                tx.fromHolderName ||
                `Account ${tx.fromAccountId}`;

              this.partnerFrequency[partner] =
                (this.partnerFrequency[partner] || 0) + 1;
            }

            // Expense
            if (tx.fromAccountId === accountId) {

              this.expenseTotal +=
                tx.transferAmount;

              const partner =
                tx.toHolderName ||
                `Account ${tx.toAccountId}`;

              this.partnerFrequency[partner] =
                (this.partnerFrequency[partner] || 0) + 1;
            }
          });

          setTimeout(() => {

            this.createIncomeExpenseChart();
            this.createPartnerChart();

          }, 100);
        },

        error: (err) => {

          console.error(
            'Error loading transaction stats',
            err
          );
        }
      });
  }

  createIncomeExpenseChart(): void {

    if (this.incomeExpenseChart) {
      this.incomeExpenseChart.destroy();
    }

    this.incomeExpenseChart = new Chart(
      'incomeExpenseChart',
      {
        type: 'doughnut',

        data: {

          labels: [
            'Income',
            'Expense'
          ],

          datasets: [
            {
              data: [
                this.incomeTotal,
                this.expenseTotal
              ],

              backgroundColor: [
                '#10b981',
                '#ef4444'
              ],

              borderWidth: 0
            }
          ]
        },

        options: {

          responsive: true,

          maintainAspectRatio: false,

          cutout: '65%',

          plugins: {

            legend: {
              position: 'bottom'
            }
          }
        }
      }
    );
  }

  createPartnerChart(): void {

    if (this.partnerChart) {
      this.partnerChart.destroy();
    }

    const sortedPartners =
      Object.entries(this.partnerFrequency)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 5);

    this.partnerChart = new Chart(
      'partnerChart',
      {
        type: 'doughnut',

        data: {

          labels:
            sortedPartners.map(
              partner => partner[0]
            ),

          datasets: [
            {
              data:
                sortedPartners.map(
                  partner => partner[1]
                ),

              backgroundColor: [
                '#2563eb',
                '#8b5cf6',
                '#06b6d4',
                '#f59e0b',
                '#ec4899'
              ],

              borderWidth: 0
            }
          ]
        },

        options: {

          responsive: true,

          maintainAspectRatio: false,

          cutout: '65%',

          plugins: {

            legend: {
              position: 'bottom'
            }
          }
        }
      }
    );
  }
}