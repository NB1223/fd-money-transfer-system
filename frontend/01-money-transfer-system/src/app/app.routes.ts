import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TransferComponent } from './transfer/transfer.component';
import { HistoryComponent } from './history/history.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
     {path: '', redirectTo: '/home', pathMatch: 'full' },
     {path: 'home', component: HomeComponent },
     { path: 'login', component: LoginComponent },
     { path: 'register', component: RegisterComponent },
     { path: 'dashboard', component: DashboardComponent },
     { path: 'transfer', component: TransferComponent },
     { path: 'history', component: HistoryComponent },
     { path: 'register', component: RegisterComponent },
     { path: 'dashboard', component: DashboardComponent },
     { path: 'transfer', component: TransferComponent },
     { path: 'history', component: HistoryComponent }
];
