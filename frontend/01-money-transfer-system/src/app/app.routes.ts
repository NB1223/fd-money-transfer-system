import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TransferComponent } from './transfer/transfer.component';
import { HistoryComponent } from './history/history.component';
import { HomeComponent } from './home/home.component';
import { AuthGuard } from './auth-guard';
import { RegisteradminComponent } from './registeradmin/registeradmin.component';
import { LoginadminComponent } from './loginadmin/loginadmin.component';
import { DashboardadminComponent } from './dashboardadmin/dashboardadmin.component';
import { RewardsComponent } from './rewards/rewards.component'; // NEW: Import RewardsComponent!


export const routes: Routes = [
     {path: '', redirectTo: '/home', pathMatch: 'full' },
     {path: 'home', component: HomeComponent },
     { path: 'login', component: LoginComponent },
     { path: 'register', component: RegisterComponent },
     { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
     { path: 'transfer', component: TransferComponent , canActivate: [AuthGuard]},
     { path: 'history', component: HistoryComponent, canActivate: [AuthGuard] },
     { path: 'rewards', component: RewardsComponent, canActivate: [AuthGuard] },
     { path: 'registeradmin', component: RegisteradminComponent },
     { path: 'loginadmin', component: LoginadminComponent },
     {path: 'dashboardadmin', component: DashboardadminComponent, canActivate: [AuthGuard] },
     
];
