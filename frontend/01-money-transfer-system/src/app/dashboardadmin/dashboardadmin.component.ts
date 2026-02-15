import { Component, ViewChild } from '@angular/core';
import { CreateComponent } from '../create/create.component';
import { AccountsComponent } from "../accounts/accounts.component";

@Component({
  selector: 'app-dashboardadmin',
  standalone: true,
  imports: [CreateComponent, AccountsComponent],
  templateUrl: './dashboardadmin.component.html',
  styleUrls: ['./dashboardadmin.component.css']
})
export class DashboardadminComponent {
  @ViewChild(AccountsComponent)
  accountsComponent!: AccountsComponent;

  refreshAccounts() {
    this.accountsComponent.loadAccounts();
}
}