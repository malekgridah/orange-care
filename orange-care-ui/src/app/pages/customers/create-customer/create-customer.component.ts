import {Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {MatHorizontalStepper, MatStepper} from '@angular/material';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import {AppSettings} from '../../../app.settings';

@Component({
  selector: 'app-create-customer',
  templateUrl: './create-customer.component.html',
  styleUrls: ['./create-customer.component.scss'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: {displayDefaultIndicatorType: false}
  }]
})
export class CreateCustomerComponent implements OnInit {

  completed = false;
  isEditable = false;
  isOptional = false;
  state: string;
  pageName = 'Contacts';
  protected appSettings: any;

  @ViewChild('stepper1') private myStepper: MatHorizontalStepper;

  constructor(private appSetting: AppSettings) {
    this.appSettings = appSetting;
  }

  ngOnInit() {
  }

  goBack() {
    switch (this.myStepper._getFocusIndex()) {
      case 1 : this.pageName = 'Contacts'; break;
      case 2 : this.pageName = 'Billing and payment information\n'; break;
      case 3 : this.pageName = 'Payment arrangement\n'; break;
      case 4 : this.pageName = 'Additional information\n'; break;
      case 5 : this.pageName = 'Confirm\n'; break;
    }
    this.myStepper.previous();
  }

  goForward() {
    this.myStepper.next();
  }

  done() {
    this.state = 'done';
    this.completed = true;
  }

  getPageName() {
    console.log(this.myStepper._getFocusIndex());
    switch (this.myStepper._getFocusIndex() + 1) {
      case 0 : this.pageName = 'Contacts'; break;
      case 1 : this.pageName = 'Billing and payment information'; break;
      case 2 : this.pageName = 'Payment arrangement'; break;
      case 3 : this.pageName = 'Additional information'; break;
      case 4 : this.pageName = 'Confirm'; break;
    }
  }
}
