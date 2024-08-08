import { Injectable } from '@angular/core';
import {UserManager, User, UserManagerSettings, Profile} from 'oidc-client';
import { Constants } from './constants';
import { Subject } from 'rxjs';
import {UrlTree} from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private _userManager: UserManager;
    private _user: User;
    private _loginChangedSubject = new Subject<boolean>();
    public userAuthenticated = false;

    public loginChanged = this._loginChangedSubject.asObservable();

    public getAccessToken = async (): Promise<string> => {
        const user = await this._userManager.getUser();
        return !!user && !user.expired ? user.access_token : null;
    }

    public getLoggedUser = async (): Promise<string> => {
        const user = await this._userManager.getUser();
        return !!user && !user.expired ? user.profile.sub : null;
    }

    public getUserProfile = async (): Promise<Profile> => {
        const user = await this._userManager.getUser();
        return !!user && !user.expired ? user.profile : null;
    }

    private get idpSettings() : UserManagerSettings {
        return {
            authority: Constants.idpAuthority,
            client_id: Constants.clientId,
            redirect_uri: `${Constants.clientRoot}/signin-callback`,
            scope: "openid profile",
            response_type: "code",
            post_logout_redirect_uri: `${Constants.clientRoot}/signout-callback`
        }
    }

    public getUser() {
        return this._user;
    }

    constructor() {
        this._userManager = new UserManager(this.idpSettings);
    }

    public login = () => {
        return this._userManager.signinRedirect();
    }

    public isAuthenticated = (): Promise<boolean> => {
        return this._userManager.getUser()
            .then(user => {
                if(this._user !== user){
                    this._loginChangedSubject.next(this.checkUser(user));
                }

                this._user = user;

                return this.checkUser(user);
            })
    }

    public finishLogin = (): Promise<User> => {
        return this._userManager.signinRedirectCallback()
            .then(user => {
                this._loginChangedSubject.next(this.checkUser(user));
                return user;
            })
    }

    public logout = () => {
        this._userManager.signoutRedirect();
    }

    public finishLogout = () => {
        this._user = null;
        return this._userManager.signoutRedirectCallback();
    }

    private checkUser = (user : User): boolean => {
        return !!user && !user.expired;
    }


    private authenticate(): boolean | UrlTree {
        this.loginChanged
            .subscribe(res => {
                this.userAuthenticated = res;
            })
        console.log(this.userAuthenticated)
        return this.userAuthenticated
    }

}
