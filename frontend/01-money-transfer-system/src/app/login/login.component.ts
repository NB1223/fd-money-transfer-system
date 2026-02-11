import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username= '';
  password = '';
  error = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ){}

  onSubmit(): void {
    const success = this.authService.login(this.username, this.password);

    if (success) {
      this.router.navigate(['/products']);
    } else {
      this.error = 'Invalid username or password';
    }
  }
  
}
