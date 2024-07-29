import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import {AuthService} from "./auth.service";
import {LocalStorageService, MemoryStorageService} from "./storage.service";




@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate, CanActivateChild {
  public userAuthenticated = false;
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.authenticate();
  }

  public isUserAuthenticated: boolean = false;

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree {
    return this.authenticate();
  }

  private authenticate(): boolean | UrlTree {
    return JSON.parse(window.sessionStorage.getItem('oidc.user:http://localhost:9000:public-client')) != null ? true : this.router.parseUrl('/login');
    // return this.auth.userAuthenticated ? true : this.router.parseUrl('/login') ;
  }
}
