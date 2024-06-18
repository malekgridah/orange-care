import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Settings } from 'src/app/app.settings.model';
import { AppSettings } from 'src/app/app.settings';


@Component({
  selector: 'app-categorias-cadastro',
  templateUrl: './categorias-cadastro.component.html',
  styleUrls: ['./categorias-cadastro.component.scss']
})
export class CategoriasCadastroComponent implements OnInit {
  form: FormGroup;
  settings: Settings;
  constructor(private formbuilder: FormBuilder, private appSettings: AppSettings) { 
      
    }

  ngOnInit() {
   // this.settings = this.appSettings;
    this.form = this.formbuilder.group({
      'nome': ['', Validators.required]
    });          
  }

  onSubmit(): void {

  }

}
