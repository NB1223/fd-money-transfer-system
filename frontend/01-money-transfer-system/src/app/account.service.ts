import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private baseUrl = 'http://localhost:9090/api/v1/account/transactions';

  constructor(private http: HttpClient) {}

  getTransactionsByPage(accountId: number, page: number, size: number): Observable<any> {
    const token = sessionStorage.getItem('jwt'); // Retrieve the JWT token from sessionStorage

    // Set the Authorization header with the Bearer token
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    // Make the HTTP GET request with the headers
    return this.http.get<any>(`${this.baseUrl}/${accountId}?page=${page}&size=${size}`, { headers });
  }
}