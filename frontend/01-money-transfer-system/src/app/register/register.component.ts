import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent {

  username = '';
  password = '';
  confirmPassword = '';

  get passwordMismatch(): boolean {
    return this.password !== this.confirmPassword;
  }

  onSubmit(form: any) {
    if (form.invalid || this.passwordMismatch) {
      alert('Fix errors before submitting');
      return;
    }

    alert('Registered Successfully');
  }

}