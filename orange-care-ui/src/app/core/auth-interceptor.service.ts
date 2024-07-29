import { AuthService } from './auth.service';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { from, Observable } from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {
    constructor(private authService: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (request.url.startsWith(environment.apiUrl)) {
            return from(
                this.authService.getAccessToken()
                    .then(token => {
                        const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
                        // or
                        // const headers = req.headers.set('Authorization', `Bearer ${token}`);
                        const authRequest = request.clone({headers});
                        return next.handle(authRequest).toPromise();
                    })
            )
        }
        else {
            return next.handle(request)
        }
    }
}
