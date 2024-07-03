import {AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatHorizontalStepper, MatPaginator} from '@angular/material';
import {EditViewCustomerComponent} from './edit-view-customer/edit-view-customer.component';
import {CustomersService} from '../customers.service';
import {ChangeStatusCustomerComponent} from './change-status-customer/change-status-customer.component';
import {AppSettings} from '../../../app.settings';
import {ActivatedRoute} from "@angular/router";
import {EccodingUriPipe} from "../../../shared/services/EncodingUri.pipe";
import {CustomerOverview} from "../customers.model";

@Component({
  selector: 'app-view-customer',
  templateUrl: './view-customer.component.html',
  styleUrls: ['./view-customer.component.scss']
})
export class ViewCustomerComponent implements OnInit {

  customerOverview: CustomerOverview = new CustomerOverview();

  protected appSettings: any;
  csCode="Customer Overview - ";

  constructor(private dialog: MatDialog,
              private route:ActivatedRoute,
              private customerService: CustomersService,
              private appSetting: AppSettings) {
    this.appSettings = appSetting;
  }


   getCustomerOverview() {
    this.route.queryParams.subscribe(param => {
      let id = param['token'];
      this.csCode += new EccodingUriPipe().transform(param['customer'],false);
       this.customerService.customerOverview(new EccodingUriPipe().transform(id.toString(), false))
          .subscribe(data => {
            console.log(data);
            this.customerOverview = data;
          })
      });
    }

  ngOnInit() {
    this.getCustomerOverview();
  }

  getNameAvatar(firstName: string, lastName: string): string {
    return firstName.charAt(0).toUpperCase() + lastName.charAt(0).toUpperCase();
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(EditViewCustomerComponent, {
      width: '600px',
      data: null
      // data: new EditCustomer(),
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result) {
        console.log('44');
      }
    });
  }

  openDialogChangeStatus(): void {
    const dialogRef = this.dialog.open(ChangeStatusCustomerComponent, {
      width: '420px',
      data: null
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result) {
        console.log('44');
      }
    });
  }


}
