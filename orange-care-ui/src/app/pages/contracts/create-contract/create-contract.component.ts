import {Component, OnInit, ViewChild} from '@angular/core';
import {MatHorizontalStepper} from '@angular/material';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';

@Component({
  selector: 'app-create-contract',
  templateUrl: './create-contract.component.html',
  styleUrls: ['./create-contract.component.scss'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: {displayDefaultIndicatorType: false}
  }]
})
export class CreateContractComponent implements OnInit {

  completed = false;
  isEditable = false;
  isOptional = false;
  state: string;
  pageName = 'Contacts';

  @ViewChild('stepper1') private myStepper: MatHorizontalStepper;

  constructor() { }

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
