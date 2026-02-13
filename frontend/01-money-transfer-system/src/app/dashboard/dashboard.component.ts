import { Component } from '@angular/core';
import { AccountService } from '../account.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  accountBalance: number = 0;
  accountId: number = 0;
  userName: string | null = '';

  constructor(private accountService: AccountService) {}

   ngOnInit(): void {
    console.log("DASHBOARD HIT");
    this.accountId = Number(sessionStorage.getItem('accountId')); // use accountId key
    this.userName = sessionStorage.getItem('username');
    this.fetchAccountBalance(this.accountId);
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

}
