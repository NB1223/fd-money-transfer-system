import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TransferService } from '../transfer.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent {
  error = " ";
  accountNo = 0;
  accNo = 0;
  amount = 0;
  remarks ?= '';
  
    constructor(
      private transferService: TransferService,
      private router: Router
    ){}
  
    onSubmit(): void {
      const success = this.transferService.transfer(this.accountNo, this.accNo, this.amount);
      if (success) {
        this.router.navigate(['/transfer']);
      } else {
        alert()
        this.error = 'Amount should be greater than 0';
      }
    }

}
