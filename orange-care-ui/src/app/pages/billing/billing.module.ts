import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {InvoicesComponent} from "./invoices/invoices.component";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../../shared/shared.module";
import {PerfectScrollbarModule} from "ngx-perfect-scrollbar";

export const routes = [
  { path: '', redirectTo: 'invoices', pathMatch: 'full'},
  { path: 'invoices', component: InvoicesComponent, data: { breadcrumb: 'Invoices' } },
];

@NgModule({
  declarations: [
    InvoicesComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule,
    PerfectScrollbarModule
  ]
})
export class BillingModule { }
