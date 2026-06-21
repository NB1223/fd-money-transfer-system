import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TransferService } from '../transfer.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [CommonModule, FormsModule, DashboardComponent],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent implements OnInit {
  error = '';
  fromAccountId = 0;
  elongatedId = 0;
  accNo: number | null = null;
  amount: number | null = null;
  remarks: string | null = '';
  balance: number = 0;
  username: string = sessionStorage.getItem('username') || 'User';
  toAccountHolderName: string = '';
  isLoadingHolderName: boolean = false;

  constructor(
    private transferService: TransferService,
    private router: Router,
    private accountService: AccountService
  ){}

  ngOnInit(): void {
    this.fromAccountId = Number(sessionStorage.getItem('accountId'));
    this.elongatedId = Number(sessionStorage.getItem('elongatedId'));
    this.fetchBalance();
  }

  private fetchBalance(): void {
    this.accountService.getAccountBalance(this.fromAccountId).subscribe({
      next: (response) => {
        this.balance = Number(response);
      },
      error: (err) => {
        console.error('Error fetching balance', err);
        this.error = 'Failed to fetch account balance';
      }
    });
  }

  onAccountNumberChange(): void {
    if (this.accNo && this.accNo > 0) {
      // Validate that the account number is in the correct format (600100100 + account_id)
      if (this.accNo < 600100100) {
        this.toAccountHolderName = 'Account not found';
        this.isLoadingHolderName = false;
        return;
      }
      this.isLoadingHolderName = true;
      this.toAccountHolderName = '';
      const actualAccountId = this.accNo - 600100100;
      this.accountService.getHolderName(actualAccountId).subscribe({
        next: (holderName) => {
          this.toAccountHolderName = holderName;
          this.isLoadingHolderName = false;
        },
        error: (err) => {
          console.error('Error fetching holder name', err);
          this.toAccountHolderName = 'Account not found';
          this.isLoadingHolderName = false;
        }
      });
    } else {
      this.toAccountHolderName = '';
    }
  }

  private generateIdempotencyKey(): string {
    // Use crypto.randomUUID if available, otherwise fallback
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const anyWin = (self as any);
    if (anyWin && typeof anyWin.crypto?.randomUUID === 'function') {
      return anyWin.crypto.randomUUID();
    }
    return `${Date.now()}-${Math.random().toString(36).slice(2, 12)}`;
  }

  onSubmit(): void {
    if (!this.accNo || !this.amount || this.amount <= 0) {
      this.error = 'Enter a valid destination account and amount > 0';
      return;
    }
    if (this.accNo < 600100100) {
      this.error = 'Account number must be at least 600100101 (format: 600100100 + account ID)';
      return;
    }
    if (this.accNo === this.fromAccountId) {
      this.error = 'Cannot transfer to the same account';
      return;
    }
    if (this.toAccountHolderName === 'Account not found') {
      this.error = 'Please select a valid destination account';
      return;
    }
    const confirmed = confirm(`Confirm transfer of ${this.amount}$ to account ${this.accNo} from account ${this.elongatedId}?`);
    if (!confirmed) return;

    const idempotencyKey = this.generateIdempotencyKey();
    this.transferService.performTransaction(this.accNo - 600100100, this.amount, idempotencyKey, this.remarks || '').subscribe({
      next: () => {
        alert('Transfer successful');
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Transfer error', err);
        this.error = err?.error?.message || err?.message || 'Transfer failed';
      }
    });
  }
}