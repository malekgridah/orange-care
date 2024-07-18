import { Routes, RouterModule, PreloadAllModules  } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';

import { PagesComponent } from './pages/pages.component';
import { SearchComponent } from './pages/search/search.component';
import { NotFoundComponent } from './pages/errors/not-found/not-found.component';
import { ErrorComponent } from './pages/errors/error/error.component';
import {AuthGuard} from "./core";


export const routes: Routes = [
    {
        path: '',
        component: PagesComponent,
        canActivate:[AuthGuard],
        canActivateChild:[AuthGuard],
        children: [
            { path: '', loadChildren: './pages/dashboard/dashboard.module#DashboardModule', data: { breadcrumb: 'Dashboard' } },
            { path: 'customers', loadChildren: './pages/customers/customers.module#CustomersModule', data: { breadcrumb: 'Customers' } },
            { path: 'contracts', loadChildren: './pages/contracts/contracts.module#ContractsModule', data: { breadcrumb: 'Contracts' } },
            { path: 'billing', loadChildren: './pages/billing/billing.module#BillingModule', data: { breadcrumb: 'Billing' } },
            { path: 'invoices', loadChildren: './pages/invoices/invoices.module#InvoicesModule', data: { breadcrumb: 'Invoices' } },
            { path: 'users', loadChildren: './pages/users/users.module#UsersModule', data: { breadcrumb: 'Users' } },
            { path: 'schedule', loadChildren: './pages/schedule/schedule.module#ScheduleModule', data: { breadcrumb: 'Schedule' } },
            { path: 'search', component: SearchComponent, data: { breadcrumb: 'Search' } },
            { path: 'search/:name', component: SearchComponent, data: { breadcrumb: 'Search' } },
        ]
    },
    { path: 'landing', loadChildren: './pages/landing/landing.module#LandingModule' },
    { path: 'login', loadChildren: './pages/login/login.module#LoginModule' },
    { path: 'error', component: ErrorComponent, data: { breadcrumb: 'Error' } },
    { path: '**', component: NotFoundComponent }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes, {
   preloadingStrategy: PreloadAllModules,  // <- comment this line for activate lazy load
   // useHash: true
});
