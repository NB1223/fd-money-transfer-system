import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {

  appName = 'MoneyFlow';
  currentYear = new Date().getFullYear();
  
  teamMembers = [
    { name: 'John Smith', role: 'Lead Developer' },
    { name: 'Sarah Johnson', role: 'Backend Engineer' },
    { name: 'Michael Chen', role: 'Frontend Developer' },
    { name: 'Emily Rodriguez', role: 'UI/UX Designer' }
  ];

}
