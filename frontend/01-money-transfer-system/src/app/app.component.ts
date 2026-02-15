import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HistoryComponent } from './history/history.component';
import { RegisterComponent } from './register/register.component';
import { TransferComponent } from './transfer/transfer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CreateComponent } from './create/create.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet, 
    RouterLink,
    RouterLinkActive,
    CommonModule,
    LoginComponent, 
    HomeComponent,
    DashboardComponent, 
    TransferComponent, 
    HistoryComponent, 
    RegisterComponent,
    NavbarComponent,
    FooterComponent,
    CreateComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = '01-money-transfer-system';

  constructor(public router: Router) {}

  shouldShowNavbar(): boolean {
    const excludedRoutes = ['/home', '/login', '/register', '/dashboardadmin', '/loginadmin', '/registeradmin'];
    return !excludedRoutes.includes(this.router.url);
  }

  
}
