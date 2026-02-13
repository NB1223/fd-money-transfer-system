import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TransferComponent } from './transfer/transfer.component';
import { HistoryComponent } from './history/history.component';
import { HomeComponent } from './home/home.component';
import { AuthGuard } from './auth-guard';

export const routes: Routes = [
     {path: '', redirectTo: '/home', pathMatch: 'full' },
     {path: 'home', component: HomeComponent },
     { path: 'login', component: LoginComponent },
     { path: 'register', component: RegisterComponent },
     { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
     { path: 'transfer', component: TransferComponent , canActivate: [AuthGuard]},
     { path: 'history', component: HistoryComponent, canActivate: [AuthGuard] }
];
