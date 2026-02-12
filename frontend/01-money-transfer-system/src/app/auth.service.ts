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

   login(username: string, password: string): Observable<{ token: string }> {
    const payload = { username, password };
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, payload);
  }

  storeSession(token: string, userId: string): void {
    sessionStorage.setItem('jwt', token);
    sessionStorage.setItem('userId', userId);
  }

  getToken(): string | null {
    return sessionStorage.getItem('jwt');
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
