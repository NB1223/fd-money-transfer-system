import { Component } from '@angular/core';
import { AdminService } from '../admin.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent {

  accounts: any[] = [];
  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.fetchAccounts(); // Replace 1 with the actual account ID
  }

  fetchAccounts(): void {
    console.log("Fetching accounts...");
    this.adminService.getAccounts().subscribe({
      next: (data) => {
        console.log("Accounts fetched:", data);
        this.accounts = data || []; // `content` contains the paginated data
      },
      error: (err) => {
        console.error('Error fetching accounts:', err);
      }
    });
  }


}
