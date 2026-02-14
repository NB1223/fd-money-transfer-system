import { Component } from '@angular/core';
import { CreateComponent } from '../create/create.component';

@Component({
  selector: 'app-dashboardadmin',
  standalone: true,
  imports: [CreateComponent],
  templateUrl: './dashboardadmin.component.html',
  styleUrls: ['./dashboardadmin.component.css']
})
export class DashboardadminComponent {}