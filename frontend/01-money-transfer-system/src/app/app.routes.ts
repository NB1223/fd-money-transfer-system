import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TransferComponent } from './transfer/transfer.component';
import { HistoryComponent } from './history/history.component';

export const routes: Routes = [
     { path: 'login', component: LoginComponent },
     { path: 'register', component: RegisterComponent },
     { path: 'dashboard', component: DashboardComponent },
     { path: 'transfer', component: TransferComponent },
     { path: 'history', component: HistoryComponent }
];
