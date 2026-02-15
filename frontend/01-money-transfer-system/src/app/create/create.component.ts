import { Component, EventEmitter, Output } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CreateService } from '../create.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-create',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent {
  error = '';
  holderName: string = '';
  balance: number = 0;
  @Output() accountCreated = new EventEmitter<void>();


  constructor(
    private createService: CreateService,
    private router: Router,
    private authService: AuthService
  ){}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/home']);
  }

  onSubmit(): void {
    if (!this.holderName || this.balance <= 0) {
      this.error = 'Enter a valid holder name and balance > 0';
      return;
    }
    this.createService.performTransaction(this.holderName, this.balance).subscribe({
      next: () => {
        this.accountCreated.emit(); 
        alert('Account created successfully');
      },
      error: (err) => {
        console.error('Account creation error', err);
        this.error = err?.error?.message || err?.message || 'Account creation failed';
      }
    });

  }

}