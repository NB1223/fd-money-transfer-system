import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {

  transferHistory: any[] = [];
  filteredHistory: any[] = [];
  currentPage: number = 0; // Start with the first page
  pageSize: number = 5; // Number of transactions per page
  totalPages: number = 0;
  currentAccountId: number = Number(sessionStorage.getItem('accountId'));
  elongatedId: number = Number(sessionStorage.getItem('elongatedId'));
  // userName: string = "John Does";
  userName: string = sessionStorage.getItem('username') || '';
  searchText: string = '';

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.fetchTransferHistory(Number(sessionStorage.getItem('accountId')), this.currentPage, this.pageSize); // Replace 1 with the actual account ID
  }

  fetchTransferHistory(accountId: number, page: number, size: number): void {
    this.accountService.getTransactionsByPage(accountId, page, size).subscribe({
      next: (data) => {
        this.transferHistory = data.content; // `content` contains the paginated data
        this.filteredHistory = this.transferHistory; // Initialize filtered history
        this.totalPages = data.totalPages; // Total pages from the backend
      },
      error: (err) => {
        console.error('Error fetching transfer history:', err);
      }
    });
  }

  onSearchChange(): void {
    if (!this.searchText.trim()) {
      this.filteredHistory = this.transferHistory;
      return;
    }

    const searchLower = this.searchText.toLowerCase();
    this.filteredHistory = this.transferHistory.filter(transaction => {
      const amount = transaction.transferAmount?.toString() || '';
      const remarks = transaction.remarks?.toLowerCase() || '';
      const toHolder = transaction.toHolderName?.toLowerCase() || '';
      const toAccountId = transaction.toAccountId ? (transaction.toAccountId + 600100100).toString() : '';
      const fromAccountId = transaction.fromAccountId ? (transaction.fromAccountId + 600100100).toString() : '';
      const timestamp = transaction.timestamp?.toLowerCase() || '';

      return (
        amount.includes(searchLower) ||
        remarks.includes(searchLower) ||
        toHolder.includes(searchLower) ||
        toAccountId.includes(searchLower) ||
        fromAccountId.includes(searchLower) ||
        timestamp.includes(searchLower)
      );
    });
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.fetchTransferHistory(Number(sessionStorage.getItem('accountId')), this.currentPage, this.pageSize); // Replace 1 with the actual account ID
    }
  }
}
