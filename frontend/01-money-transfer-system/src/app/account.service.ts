import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private baseUrl = 'http://localhost:9090/api/v1/account/transactions';

  constructor(private http: HttpClient) {}

  getTransactionsByPage(accountId: number, page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${accountId}?page=${page}&size=${size}`);
  }
}