import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router, RouterLink, RouterLinkActive, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username = '';
  password = '';
  error = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ){}

    onSubmit(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        this.authService.storeSession(response.accountId, response.username, response.token);
        alert('Login successful');
        this.router.navigate(['/dashboard'], { replaceUrl: true });
      },
      error: (err) => {
        console.error(err);
        this.error = 'Invalid username or password';
      }
    });
  }
  
}
