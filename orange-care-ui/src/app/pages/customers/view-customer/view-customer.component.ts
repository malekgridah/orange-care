import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatHorizontalStepper, MatPaginator} from '@angular/material';
import {EditViewCustomerComponent} from './edit-view-customer/edit-view-customer.component';
import {EditCustomer} from '../customers.service';
import {ChangeStatusCustomerComponent} from './change-status-customer/change-status-customer.component';
import {AppSettings} from '../../../app.settings';

@Component({
  selector: 'app-view-customer',
  templateUrl: './view-customer.component.html',
  styleUrls: ['./view-customer.component.scss']
})
export class ViewCustomerComponent implements OnInit {

  protected appSettings: any;

  constructor(private dialog: MatDialog, private appSetting: AppSettings) {
    this.appSettings = appSetting;
  }

  ngOnInit() {
  }

  getNameAvatar(firstName: string, lastName: string): string {
    return firstName.charAt(0).toUpperCase() + lastName.charAt(0).toUpperCase();
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(EditViewCustomerComponent, {
      width: '600px',
      data: new EditCustomer(),
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
      data: new EditCustomer(),
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result) {
        console.log('44');
      }
    });
  }

}
