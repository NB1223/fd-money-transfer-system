import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
private baseUrl = 'http://localhost:9090/api/v1/admin/account/all';

  constructor(private http: HttpClient) {}

  getAccounts(): Observable<any> {
    const token = sessionStorage.getItem('jwt'); // Retrieve the JWT token from sessionStorage

    // Set the Authorization header with the Bearer token
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    console.log(this.baseUrl);
    // Make the HTTP GET request with the headers
    return this.http.get<any>(`${this.baseUrl}`, { headers });
  }
}