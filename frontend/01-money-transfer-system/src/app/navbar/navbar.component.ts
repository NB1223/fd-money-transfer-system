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

  constructor(private authService: AuthService, private router: Router) {}

    logout(): void {
      this.authService.logout();
      alert('Logged out successfully');
      this.router.navigate(['/login']);

    }

  navItems = [
    { name: 'Profile', path: '/dashboard' },
    { name: 'Transfer', path: '/transfer' },
    { name: 'History', path: '/history' }
  ];

}
