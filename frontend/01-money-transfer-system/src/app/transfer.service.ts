import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class TransferService {
  private baseUrl = 'http://localhost:9090/api/v1/transfers';
  constructor(private http: HttpClient) {}

  performTransaction(toAccountId: number, amount: number, idempotencyKey: string): Observable<any> {
      const token = sessionStorage.getItem('jwt'); // Retrieve the JWT token from sessionStorage
      const fromAccountId = sessionStorage.getItem('accountId'); // Retrieve the account ID from sessionStorage
      // Set the Authorization header with the Bearer token
      const headers = new HttpHeaders({
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      });

      const payload = {
        fromAccountId: Number(fromAccountId), // Convert the account ID back to the original value
        toAccountId,
        amount,
        idempotencyKey
      };
      console.log(`${this.baseUrl}`)
      // Make the HTTP POST request with the headers
      return this.http.post<any>(`${this.baseUrl}`, payload, { headers });
  }

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
