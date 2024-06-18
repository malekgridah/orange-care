import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-blank',
  templateUrl: './blank.component.html',
  styleUrls: ['./blank.component.scss']
})
export class BlankComponent {
  form: FormGroup;
  constructor(public formBuilder: FormBuilder) { 
    this.form = this.formBuilder.group({
      'codigo': [null, null],
      'nome': [null, Validators.compose([Validators.required, Validators.minLength(3)])]
    });
  }

  public onSubmit(values: Object): void {
    if (this.form.valid) {
      console.log('Funciona................')
    }
  }

  
}
