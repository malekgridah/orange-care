import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CreateContractComponent} from './create-contract/create-contract.component';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {SearchContractComponent} from './search-contract/search-contract.component';
import {PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ContractOverviewComponent} from "./contract-overview/contract-overview.component";
import {NgxPaginationModule} from "ngx-pagination";
import {PipesModule} from "../../theme/pipes/pipes.module";
import {MatTreeModule} from "@angular/material/tree";


export const routes = [
  { path: '', redirectTo: 'search', pathMatch: 'full'},
  { path: 'search', component: SearchContractComponent, data: { breadcrumb: 'Search Contract' } },
  { path: 'create-contract', component: CreateContractComponent, data: { breadcrumb: 'Create Contract'}},
  { path: 'overview', component: ContractOverviewComponent, data: { breadcrumb: 'View Customer' } }
];


@NgModule({
  declarations: [
    SearchContractComponent,
    CreateContractComponent,
    ContractOverviewComponent
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        SharedModule,
        PerfectScrollbarModule,
        FormsModule,
        ReactiveFormsModule,
        NgxPaginationModule,
        PipesModule,
        MatTreeModule
    ]
})
export class ContractsModule { }
