import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedIn = false;
  private readonly apiUrl = 'http://localhost:9090/api/auth';

  constructor(private http: HttpClient) {}
  //const jwt = require('jsonwebtoken');

   register(username: string, password: string): Observable<number> {
    const payload = { username, password };
    return this.http.post<number>(`${this.apiUrl}/register`, payload);
  }

  registerAdmin(username: string, password: string): Observable<number> {
    const payload = { username, password };
    return this.http.post<number>(`${this.apiUrl}/admin/register`, payload);
  }

   login(username: string, password: string): Observable<{ accountId: number; username: string; token: string }> {
    const payload = { username, password };
    return this.http.post<{ accountId: number; username: string; token: string }>(`${this.apiUrl}/login`, payload);
  }

    loginAdmin(username: string, password: string): Observable<{ userId: number; username: string; token: string }> {
    const payload = { username, password };
    return this.http.post<{ userId: number; username: string; token: string }>(`${this.apiUrl}/admin/login`, payload);
  }
  

  storeSession(accountId: number, username: string, token: string): void {
    sessionStorage.setItem('elongatedId', (accountId + 600100100).toString());
    sessionStorage.setItem('jwt', token);
    sessionStorage.setItem('accountId', accountId.toString());
    sessionStorage.setItem('username', username);
    this.loggedIn = true;
  }

  getToken(): string | null {
    return sessionStorage.getItem('jwt');
  }

  getUserId(): string | null {
    return sessionStorage.getItem('userId');
  }

  getUsername(): string | null {
    return sessionStorage.getItem('username');
  }

  logout() {
    this.loggedIn = false;
    sessionStorage.clear();
    console.log('User logged out');
  }

  isAuthenticated(): boolean {
    return this.loggedIn;
  }

 
}