import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {

  transferHistory: any[] = [];
  currentPage: number = 0; // Start with the first page
  pageSize: number = 5; // Number of transactions per page
  totalPages: number = 0;
  currentAccountId: number = Number(sessionStorage.getItem('accountId'));
  elongatedId: number = Number(sessionStorage.getItem('elongatedId'));
  // userName: string = "John Does";
  userName: string = sessionStorage.getItem('username') || '';

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.fetchTransferHistory(Number(sessionStorage.getItem('accountId')), this.currentPage, this.pageSize); // Replace 1 with the actual account ID
  }

  fetchTransferHistory(accountId: number, page: number, size: number): void {
    this.accountService.getTransactionsByPage(accountId, page, size).subscribe({
      next: (data) => {
        this.transferHistory = data.content; // `content` contains the paginated data
        this.totalPages = data.totalPages; // Total pages from the backend
      },
      error: (err) => {
        console.error('Error fetching transfer history:', err);
      }
    });
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.fetchTransferHistory(Number(sessionStorage.getItem('accountId')), this.currentPage, this.pageSize); // Replace 1 with the actual account ID
    }
  }
}
