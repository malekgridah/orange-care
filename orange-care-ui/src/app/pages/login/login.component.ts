import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators} from '@angular/forms';
import { emailValidator } from '../../theme/utils/app-validators';
import { AppSettings } from '../../app.settings';
import { Settings } from '../../app.settings.model';
import { AuthService } from './auth.service';

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
      'usuario': [null, Validators.compose([Validators.required, emailValidator])],
      'senha': [null, Validators.compose([Validators.required, Validators.minLength(3)])],
      'rememberMe': false
    });
  }

 /*  login(usuario: string, senha: string) {
    const val = this.form.value;

    if(this.form.valid) {
    this.auth.login(val.usuario, val.senha);
    this.router.navigate(['/']);
    }
  } */

 /*  login() {
    const val = this.form.value;

    if (this.form.valid) {
      this.auth.login(val.usuario, val.senha)
        .subscribe(
           () => {
             console.log(val.usuario);
             this.router.navigate['/'];
           }
        )

      //  this.form.reset();
    }
  } */

  public onSubmit(values:Object):void {
    if (this.form.valid) {
    //  this.auth.login(val.usuario, val.senha)
    //  .subscribe(
    //     () => {
           this.router.navigate['/'];
   //      }
   //   )
    }
  }

  ngAfterViewInit(){
    this.settings.loadingSpinner = false; 
  }
}