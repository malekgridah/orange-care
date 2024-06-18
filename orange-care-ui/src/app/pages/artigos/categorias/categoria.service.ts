import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Categoria } from './model/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  categoriaUrl = 'http://localhost:8080/categorias';

  constructor(private http: HttpClient) { }

  pesquisar(): Observable<Categoria[]> {
    const headers = new HttpHeaders()
          .append('Authorization', 'Basic YWRtaW5AbWVzaG9wLmNvbTphZG1pbg==');
    return this.http.get<Categoria[]>(`${this.categoriaUrl}`, { headers });
  }
}
