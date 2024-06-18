import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CreateContractComponent} from './create-contract/create-contract.component';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';
import {SearchContractComponent} from './search-contract/search-contract.component';
import {PerfectScrollbarModule} from 'ngx-perfect-scrollbar';


export const routes = [
  { path: '', redirectTo: 'search', pathMatch: 'full'},
  { path: 'search', component: SearchContractComponent, data: { breadcrumb: 'Search Contract' } },
  { path: 'create-contract', component: CreateContractComponent, data: { breadcrumb: 'Create Contract' } }
];


@NgModule({
  declarations: [
    SearchContractComponent,
    CreateContractComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule,
    PerfectScrollbarModule
  ]
})
export class ContractsModule { }
