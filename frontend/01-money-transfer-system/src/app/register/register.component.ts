import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';
import { Router, RouterLink, RouterLinkActive, RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent {

  username = '';
  password = '';
  confirmPassword = '';
  userId: number = 0;
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  get passwordMismatch(): boolean {
    return this.password !== this.confirmPassword;
  }

  onSubmit(form: any) {
    if (form.invalid || this.passwordMismatch) {
      alert('Fix errors before submitting');
      return;
    }

    this.errorMessage = ''; // Clear previous errors
    this.authService.register(this.username, this.password).subscribe({
      next: (id) => {
        this.userId = id + 600100100;
        alert(`User registered successfully with ID: ${this.userId}`);
        this.router.navigate(['/login']);

      },
      error: (err) => {
        console.error(err);
        
        // Check for duplicate username error
        if (err.status === 400 || (err.error && err.error.message && err.error.message.includes('Duplicate'))) {
          this.errorMessage = 'Username already exists. Please choose a different username.';
          // alert('Username already exists. Please choose a different username.');
        } 
        if (err.error && err.error.message && err.error.message.includes('Account not created')) {
          this.errorMessage = 'Account has not been created. Please contact Admin.';
        } 
          
        else {
          this.errorMessage = 'Registration failed. Please try again.';
          alert('Registration failed. Please try again.');
        }
      }
    });

  }

}