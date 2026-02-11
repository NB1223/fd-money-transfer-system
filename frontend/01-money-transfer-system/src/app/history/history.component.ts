import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {

   transactions = [
    
  { type: "debit", name: 'Transfer to A', amount: 20, date: "jan 45, 182 . 89:56 am" ,status: "Success"},
  { type: "debit", name: 'Transfer to B', amount: 30, date: "feb 09, 183 . 55:34 pm", status: "Success"  },
  { type: "debit", name: 'Transfer to A', amount: 60, date: "jun 23, 186 . 23:78 pm", status: "Success"  },
  { type: "credit", name: 'Recieved from C', amount: 20, date: "jan 45, 182 . 76:56 pm", status: "Failure"  },
  { type: "credit", name: 'Recieved from J', amount: 80, date: "aug 65, 183 . 54:45 am", status: "Success"  },
  { type: "debit", name: 'Transfer to 8', amount: 10, date: "dec 65, 142 . 67:56 am", status: "Failure"  },
  { type: "debit", name: 'Transfer to K', amount: 40, date: "jan 25, 185 . 65:56 am", status: "Success"  },
  { type: "credit", name: 'Recieved from E', amount: 20, date: "mar 12, 181 . 45:45 pm", status: "Failure"  },
  { type: "credit", name: 'Recieved from M', amount: 30, date: "jul 19, 185 . 23:45 am", status: "Success"  },
  { type: "debit", name: 'Transfer to W', amount: 70, date: "sep 90, 188 . 56:67 pm", status: "Success"  }
  ];
}
