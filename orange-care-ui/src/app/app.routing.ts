import { Routes, RouterModule, PreloadAllModules  } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';

import { PagesComponent } from './pages/pages.component';
import { BlankComponent } from './pages/blank/blank.component';
import { SearchComponent } from './pages/search/search.component';
import { NotFoundComponent } from './pages/errors/not-found/not-found.component';
import { ErrorComponent } from './pages/errors/error/error.component';
import { CategoriasCadastroComponent } from './pages/artigos/categorias/categorias-cadastro/categorias-cadastro.component';
import { CategoriasPesquisaComponent } from './pages/artigos/categorias/categorias-pesquisa/categorias-pesquisa.component';
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
            { path: 'users', loadChildren: './pages/users/users.module#UsersModule', data: { breadcrumb: 'Users' } },
            { path: 'dynamic-menu', loadChildren: './pages/dynamic-menu/dynamic-menu.module#DynamicMenuModule', data: { breadcrumb: 'Dynamic Menu' }  },
            { path: 'ui', loadChildren: './pages/ui/ui.module#UiModule', data: { breadcrumb: 'UI' } },
            { path: 'mailbox', loadChildren: './pages/mailbox/mailbox.module#MailboxModule', data: { breadcrumb: 'Mailbox' } },
            { path: 'chat', loadChildren: './pages/chat/chat.module#ChatModule', data: { breadcrumb: 'Chat' } },
            { path: 'form-controls', loadChildren: './pages/form-controls/form-controls.module#FormControlsModule', data: { breadcrumb: 'Form Controls' } },
            { path: 'tables', loadChildren: './pages/tables/tables.module#TablesModule', data: { breadcrumb: 'Tables' } },
            { path: 'schedule', loadChildren: './pages/schedule/schedule.module#ScheduleModule', data: { breadcrumb: 'Schedule' } },
            { path: 'maps', loadChildren: './pages/maps/maps.module#MapsModule', data: { breadcrumb: 'Maps' } },
            { path: 'charts', loadChildren: './pages/charts/charts.module#ChartsModule', data: { breadcrumb: 'Charts' } },
            { path: 'drag-drop', loadChildren: './pages/drag-drop/drag-drop.module#DragDropModule', data: { breadcrumb: 'Drag & Drop' } },
            { path: 'icons', loadChildren: './pages/icons/icons.module#IconsModule', data: { breadcrumb: 'Material Icons' } },
            { path: 'blank', component: BlankComponent, data: { breadcrumb: 'Blank page' } },
            { path: 'search', component: SearchComponent, data: { breadcrumb: 'Search' } },
            { path: 'search/:name', component: SearchComponent, data: { breadcrumb: 'Search' } },
            { path: 'categorias/nova', component: CategoriasCadastroComponent, data: { breadcrumb: 'Nova Categoria'} },
            { path: 'categorias', component: CategoriasPesquisaComponent, data: { breadcrumb: 'Listar Categorias '}}
          /*   { path: 'categorias', component: CategoriasComponent, data: { breadcrumb: 'Categorias'} },
            { path: 'categorias/nova', component: CategoriasCadastroComponent, data: { breadcrumb: 'Nova Categoria'} },
            { path: 'subcategorias', component: SubcategoriasComponent, data: { breadcrumb: 'Subcategorias'} },
            { path: 'produtos', component: ProdutosComponent, data: { breadcrumb: 'Produtos'} } */
        ]
    },
    { path: 'landing', loadChildren: './pages/landing/landing.module#LandingModule' },
    { path: 'login', loadChildren: './pages/login/login.module#LoginModule' },
    { path: 'register', loadChildren: './pages/register/register.module#RegisterModule' },
    { path: 'error', component: ErrorComponent, data: { breadcrumb: 'Error' } },
    { path: '**', component: NotFoundComponent }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes, {
   preloadingStrategy: PreloadAllModules,  // <- comment this line for activate lazy load
   // useHash: true
});
