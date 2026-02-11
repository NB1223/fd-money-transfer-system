import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedIn = false;
  //const jwt = require('jsonwebtoken');

  login(username: string, password: string): boolean {
    if (username === 'admin' && password === 'admin') {
      this.loggedIn = true;
      return true;
    }
    this.loggedIn = false;
    return false;
  }

  logout() {
    this.loggedIn = false;
    console.log('User logged out');
  }

  isAuthenticated(): boolean {
    return this.loggedIn;
  }

  getToken(){
    
    
  }
}
