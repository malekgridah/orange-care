import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchInvoiceComponent } from './search-invoice/search-invoice.component';
import {SharedModule} from "../../shared/shared.module";
import {RouterModule} from "@angular/router";
import {PerfectScrollbarModule} from "ngx-perfect-scrollbar";
import {ReactiveFormsModule} from "@angular/forms";
import { InvoiceOverviewComponent } from './invoice-overview/invoice-overview.component';

export const routes = [
    { path: '', redirectTo: 'search', pathMatch: 'full'},
    { path: 'search', component: SearchInvoiceComponent, data: { breadcrumb: 'Search Invoice' } },
    { path: 'overview', component: InvoiceOverviewComponent, data: { breadcrumb: 'View Invoice' } },
];

@NgModule({
  declarations: [SearchInvoiceComponent, InvoiceOverviewComponent],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        SharedModule,
        PerfectScrollbarModule,
        ReactiveFormsModule
    ]
})
export class InvoicesModule { }
