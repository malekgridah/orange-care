import {Component, OnInit} from '@angular/core';
import { FormGroup, FormBuilder, Validators} from '@angular/forms';
import { AppSettings } from '../../app.settings';
import { Settings } from '../../app.settings.model';
import {AuthService} from "../../core";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit{
  public isUserAuthenticated: boolean = false;

  public form:FormGroup;
  public settings: Settings;
  hide = true;

  get username() {
    return this.form.get('username');
  }

  get password() {
    return this.form.get('password');
  }

  public onSubmit(values:Object):void {

  }

  constructor(public appSettings:AppSettings, public fb: FormBuilder, private _authService: AuthService) {
    this.form = this.fb.group({
      'username': [null, Validators.compose([Validators.required])],
      'password': [null, Validators.compose([Validators.required, Validators.minLength(3)])],
      'rememberMe': false
    });
    this.settings = this.appSettings.settings;
    this.settings.loadingSpinner = false
    // For debugging:
  }

  ngOnInit(): void {
    this._authService.loginChanged
        .subscribe(res => {
          this.isUserAuthenticated = res;
        })
  }

  public login = () => {
    this._authService.login();
  }

  public logout = () => {
    this._authService.logout();
  }


}
