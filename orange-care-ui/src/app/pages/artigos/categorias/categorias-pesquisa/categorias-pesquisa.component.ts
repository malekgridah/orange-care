import { Component, OnInit } from '@angular/core';
import { CategoriaService } from '../categoria.service';
import { Categoria } from '../model/categoria';
import { Settings } from 'src/app/app.settings.model';
import { AppSettings } from 'src/app/app.settings';
import { MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs';
import { DataSource } from '@angular/cdk/collections';

@Component({
  selector: 'app-categorias-pesquisa',
  templateUrl: './categorias-pesquisa.component.html',
  styleUrls: ['./categorias-pesquisa.component.scss']
})
export class CategoriasPesquisaComponent implements OnInit {
  categorias: Categoria[];
  dataSource = new CategoriaDataSource(this.categoriaService);
  displayedColumns = ['codigo', 'nome','actions'];
  settings: Settings;
  constructor(private categoriaService: CategoriaService
              , private appSettings: AppSettings) { }

  ngOnInit() {
  //  this.pesquisar();
  }


  }

  export class CategoriaDataSource extends DataSource<any> {

    constructor(private categoriaService: CategoriaService) {
      super();
    }

    connect(): Observable<Categoria[]> {
      return this.categoriaService.pesquisar();
    }

    disconnect() {}
  }


