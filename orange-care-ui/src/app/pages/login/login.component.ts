import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators} from '@angular/forms';
import { emailValidator } from '../../theme/utils/app-validators';
import { AppSettings } from '../../app.settings';
import { Settings } from '../../app.settings.model';
import {AuthService} from "../../core";
import {filter} from "rxjs/operators";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  public form:FormGroup;
  public settings: Settings;
  hide = true;
  constructor(public appSettings:AppSettings, public fb: FormBuilder, public router:Router,
             private auth: AuthService) {
    this.settings = this.appSettings.settings; 
    this.form = this.fb.group({
      'usuario': [null, Validators.compose([Validators.required])],
      'senha': [null, Validators.compose([Validators.required, Validators.minLength(3)])],
      'rememberMe': false
    });
  }

  //  login(usuario: string, senha: string) {
  //   const val = this.form.value;
  //
  //   if(this.form.valid) {
  //   this.auth.login(val.usuario, val.senha);
  //   this.router.navigate(['/']);
  //   }
  // }

  //  login() {
  //   const val = this.form.value;
  //
  //   if (this.form.valid) {
  //     this.auth.login(val.usuario, val.senha)
  //       .subscribe(
  //          () => {
  //            console.log(val.usuario);
  //            this.router.navigate['/'];
  //          }
  //       )
  //
  //     //  this.form.reset();
  //   }
  // }

  get email() {
    return this.form.get('usuario');
  }

  get password() {
    return this.form.get('senha');
  }

  public onSubmit(values:Object):void {
    if (this.form.valid) {
     this.auth.login(this.email.value, this.password.value)
         .pipe(filter(authenticated => authenticated))
          .subscribe(
         () => this.router.navigateByUrl('/'),
     )
    }
  }

  ngAfterViewInit(){
    this.settings.loadingSpinner = false; 
  }
}
