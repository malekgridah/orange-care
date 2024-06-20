import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {environment} from "../../../environments/environment";


const BASE_URL = `${environment.apiUrl}/authenticate`;

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<void> {
     const body = {username:username, password:password};
     
     // const headers = new HttpHeaders();
     // headers.append('Authorization', 'Basic YW5ndWxhcjp3ZWJhbmd1bGFy');
     // headers.append('Content-Type', 'application/x-www-form-urlencoded');

     return this.http.post<any>(BASE_URL, body)
      .pipe(map(response => {
        console.log(response);
      }));
  }
}
