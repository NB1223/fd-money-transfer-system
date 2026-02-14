import { CommonModule } from '@angular/common';
import { Component, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive, RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  currentDate: string = '';
  currentTime: string = '';

  ngOnInit() {
    this.updateDateTime();
    setInterval(() => this.updateDateTime(), 1000);
  }

  constructor(private authService: AuthService, private router: Router) {}

    logout(): void {
      this.authService.logout();
      alert('Logged out successfully');
      this.router.navigate(['/login']);

    }


  updateDateTime() {
    const now = new Date();

    this.currentDate = now.toLocaleDateString(undefined, {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });

    this.currentTime = now.toLocaleTimeString([], {
      hour: '2-digit',
      minute: '2-digit'
    });
  }


}
