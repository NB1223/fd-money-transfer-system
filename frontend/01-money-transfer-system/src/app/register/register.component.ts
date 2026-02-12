import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent {

  username = '';
  password = '';
  confirmPassword = '';
  userId: number | null = null;

  constructor(private authService: AuthService) {}

  get passwordMismatch(): boolean {
    return this.password !== this.confirmPassword;
  }

  onSubmit(form: any) {
    if (form.invalid || this.passwordMismatch) {
      alert('Fix errors before submitting');
      return;
    }

    this.authService.register(this.username, this.password).subscribe({
      next: (id) => {
        this.userId = id;
        alert(`User registered successfully with ID: ${id}`);
      },
      error: (err) => {
        console.error(err);
        alert('Registration failed');
      }
    });

    alert('Registered Successfully');
  }



}