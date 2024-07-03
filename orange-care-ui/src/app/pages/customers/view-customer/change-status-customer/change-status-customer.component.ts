import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {ViewCustomerComponent} from '../view-customer.component';

@Component({
  selector: 'app-change-status-customer',
  templateUrl: './change-status-customer.component.html',
  styleUrls: ['./change-status-customer.component.scss']
})
export class ChangeStatusCustomerComponent implements OnInit {

  constructor(
      public dialogRef: MatDialogRef<ViewCustomerComponent>,
      @Inject(MAT_DIALOG_DATA) public customer: any
  ) {}

  close(): void {
    this.dialogRef.close();
  }
  ngOnInit() {
  }

}
