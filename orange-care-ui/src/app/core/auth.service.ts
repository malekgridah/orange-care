import { Injectable } from '@angular/core';
import { BehaviorSubject, iif, Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, share, switchMap, tap } from 'rxjs/operators';
import { TokenService } from './token.service';
import {environment} from "../../environments/environment";

const BASE_URL = `${environment.apiUrl}`;
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private token: TokenService
  ) {}

  check() {
    return this.token.valid();
  }

  login(username: string, password: string) {
    return this.http
      .post<any>(BASE_URL + '/authenticate', { username, password })
      .pipe(
        tap(token => {
          this.token.set({ access_token: token.token, token_type: 'bearer', token_status: token.isSuccessful });
        }),
        map(() => this.check())
      );
  }


  logout() {
    // return this.http.post('/auth/logout', {}).pipe(
    //   tap(() => this.token.clear()),
    //   map(() => !this.check())
    // );
    return of({}).pipe(
      tap(() => this.token.clear()),
      map(() => !this.check())
    );
  }

}
