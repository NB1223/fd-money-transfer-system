import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive, RouterModule } from '@angular/router';

@Component({
  selector: 'app-loginadmin',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive, RouterModule],
  templateUrl: './loginadmin.component.html',
  styleUrl: './loginadmin.component.css'
})
export class LoginadminComponent {

  username = '';
  password = '';
  error = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ){}

    onSubmit(): void {
    this.authService.loginAdmin(this.username, this.password).subscribe({
      next: (response) => {
        this.authService.storeSession(response.userId, response.username, response.token);
        alert('Login successful');
        this.router.navigate(['/dashboardadmin']);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Invalid username or password';
      }
    });
  }
  
}
