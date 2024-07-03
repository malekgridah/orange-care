import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {ViewCustomerComponent} from '../view-customer.component';
// import {EditCustomer} from '../../customers.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-edit-view-customer',
  templateUrl: './edit-view-customer.component.html',
  styleUrls: ['./edit-view-customer.component.scss']
})
export class EditViewCustomerComponent implements OnInit {

  constructor(
      public dialogRef: MatDialogRef<ViewCustomerComponent>,
      @Inject(MAT_DIALOG_DATA) public customer: any
  ) {}


  // editCustomerForm: FormGroup = new FormGroup({
  //   fName: new FormControl('', [Validators.minLength(4)]),
  //   lName: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   streetName: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   streetNumber: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   city: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   zip: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   country: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   county: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   currency: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   password: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   language: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   addressNote: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //   customerGroup: new FormControl('', [Validators.required, Validators.minLength(4)]),
  //
  //   email: new FormControl('', [Validators.required, Validators.email]),
  //
  //   phone: new FormControl('', [
  //     Validators.required,
  //     Validators.min(1),
  //     Validators.pattern(/^-?(0|[1-9]\d*)?$/),
  //   ])
  // });
  //
  close(): void {
    this.dialogRef.close();
  }
  //
  ngOnInit() {
  //   if (this.customer) {
  //     this.editCustomerForm.patchValue(this.customer);
  //   }
  }


}
