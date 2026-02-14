import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-registeradmin',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive, RouterModule],
  templateUrl: './registeradmin.component.html',
  styleUrl: './registeradmin.component.css'
})
export class RegisteradminComponent {

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
      this.authService.registerAdmin(this.username, this.password).subscribe({
        next: (id) => {
          this.userId = id;
          alert(`Admin registered successfully with ID: ${id}`);
          this.router.navigate(['/loginadmin']);
  
        },
        error: (err) => {
          console.error(err);
          
          // Check for duplicate username error
          if (err.status === 400 || (err.error && err.error.message && err.error.message.includes('Duplicate'))) {
            this.errorMessage = 'Admin already exists. Please choose a different username.';
            // alert('Username already exists. Please choose a different username.');
          } else {
            this.errorMessage = 'Registration failed. Please try again.';
            alert('Registration failed. Please try again.');
          }
        }
      });
  
    }
}

