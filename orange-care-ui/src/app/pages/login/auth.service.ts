import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  oauthTokenUrl = 'http://localhost:8080/oauth/token';

  constructor(private http: HttpClient) { }

  login(usuario: string, senha: string): Observable<void> {
     const body = `username=admin@meshop.com&password=admin&grant_type=password`;
     
     const headers = new HttpHeaders();
     headers.append('Authorization', 'Basic YW5ndWxhcjp3ZWJhbmd1bGFy');
     headers.append('Content-Type', 'application/x-www-form-urlencoded');

     return this.http.post<any>(this.oauthTokenUrl, body, { headers })
      .pipe(map(response => {
        console.log(response);
      }));
  }
 

}
