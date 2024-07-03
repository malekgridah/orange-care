import {AfterViewInit, Component, ElementRef, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';

@Component({
  selector: 'app-billing-customer-tab',
  templateUrl: './billing-customer-tab.component.html',
  styleUrls: ['./billing-customer-tab.component.scss']
})
export class BillingCustomerTabComponent implements OnChanges, AfterViewInit {


  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  ngAfterViewInit(): void {
  }


}
