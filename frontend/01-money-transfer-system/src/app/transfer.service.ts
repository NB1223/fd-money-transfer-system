import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  constructor() { }
  private transferSuccess = false;
  
   transfer(accountNo: number, accNo: number, amount: number): boolean {
    if (amount >= 0  && accNo!== accountNo) {
      this.transferSuccess=true;
      return true;
    }
    else {
      return false;
    }
  }


}
