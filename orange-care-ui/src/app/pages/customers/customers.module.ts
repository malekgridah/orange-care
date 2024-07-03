import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CreateCustomerComponent} from './create-customer/create-customer.component';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {SearchCustomerComponent} from './search-customer/search-customer.component';
import {PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {TablesService} from '../tables/tables.service';
import {CustomersService} from './customers.service';
import { ViewCustomerComponent } from './view-customer/view-customer.component';
import { ContractCustomerTabComponent } from './view-customer/contract-customer-tab/contract-customer-tab.component';
import { BillingCustomerTabComponent } from './view-customer/billing-customer-tab/billing-customer-tab.component';
import { CustomerDetailsCustomerTabComponent } from './view-customer/customer-details-customer-tab/customer-details-customer-tab.component';
import { EditViewCustomerComponent } from './view-customer/edit-view-customer/edit-view-customer.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {UserDialogComponent} from '../users/user-dialog/user-dialog.component';
import { ChangeStatusCustomerComponent } from './view-customer/change-status-customer/change-status-customer.component';
import { CustomerInvoicesComponent } from './view-customer/billing-customer-tab/customer-invoices/customer-invoices.component';
import { CustomerBillingAccountsComponent } from './view-customer/billing-customer-tab/customer-billing-accounts/customer-billing-accounts.component';


export const routes = [
  { path: '', redirectTo: 'search', pathMatch: 'full'},
  { path: 'search', component: SearchCustomerComponent, data: { breadcrumb: 'Search Customer' } },
  { path: 'create-customer', component: CreateCustomerComponent, data: { breadcrumb: 'Create Customer' } },
  { path: 'view', component: ViewCustomerComponent, data: { breadcrumb: 'View Customer' } }
];


@NgModule({
  declarations: [
      SearchCustomerComponent,
      CreateCustomerComponent,
      ViewCustomerComponent,
      ContractCustomerTabComponent,
      BillingCustomerTabComponent,
      CustomerDetailsCustomerTabComponent,
      EditViewCustomerComponent,
      ChangeStatusCustomerComponent,
      CustomerInvoicesComponent,
      CustomerBillingAccountsComponent
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        SharedModule,
        PerfectScrollbarModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [
        CustomersService
    ],
    entryComponents: [
        EditViewCustomerComponent,
        ChangeStatusCustomerComponent
    ]
})
export class CustomersModule { }
