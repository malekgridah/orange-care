import { Component, ViewChild} from '@angular/core';
import { AppSettings } from './app.settings';
import { Settings } from './app.settings.model';
import {AuthService} from "./core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  public settings: Settings;
  constructor(public appSettings:AppSettings, private _authService: AuthService){
      this.settings = this.appSettings.settings;
    this._authService.loginChanged
        .subscribe(userAuthenticated => {
          this.userAuthenticated = userAuthenticated;
        })
  }

  public userAuthenticated = false;


  ngOnInit(): void {
    this._authService.isAuthenticated()
        .then(userAuthenticated => {
          this.userAuthenticated = userAuthenticated;
        })
  }
}
