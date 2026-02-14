import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CreateService {
private baseUrl = 'http://localhost:9090/api/v1/admin/account/create';
  constructor(private http: HttpClient) {}

  performTransaction(holderName: string, balance: number): Observable<any> {
      const token = sessionStorage.getItem('jwt'); // Retrieve the JWT token from sessionStorage
      // const fromAccountId = sessionStorage.getItem('accountId'); // Retrieve the account ID from sessionStorage
      // Set the Authorization header with the Bearer token
      const headers = new HttpHeaders({
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      });

      const payload = {
        holderName,
        balance,
      };
      console.log(`${this.baseUrl}`)
      // Make the HTTP POST request with the headers
      return this.http.post<any>(`${this.baseUrl}`, payload, { headers });
  }

}