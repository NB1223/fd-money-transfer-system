import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private baseUrl = 'http://localhost:9090/api/v1/account';

  constructor(private http: HttpClient) {}

  getTransactionsByPage(accountId: number, page: number, size: number): Observable<any> {
    const token = sessionStorage.getItem('jwt'); // Retrieve the JWT token from sessionStorage

    // Set the Authorization header with the Bearer token
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    console.log(`${this.baseUrl}/transactions/${accountId}?page=${page}&size=${size}`)
    // Make the HTTP GET request with the headers
    return this.http.get<any>(`${this.baseUrl}/transactions/${accountId}?page=${page}&size=${size}`, { headers });
  }

  getAccountBalance(accountId: number): Observable<{balance: any}> {
    console.log("HIT IN SERVICE")
      const token = sessionStorage.getItem('jwt');
     const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    console.log(`${this.baseUrl}/${accountId}/balance`);
    return this.http.get<{balance: any}>(`${this.baseUrl}/${accountId}/balance`,{headers});
  }
}