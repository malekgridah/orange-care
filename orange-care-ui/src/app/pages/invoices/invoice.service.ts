import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {InvoiceSearchRequest, InvoiceSearchResponse} from "./invoices.model";


const BASE_URL = `${environment.apiUrl}/payment/api/`;

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  constructor(private httpClient: HttpClient) { }

  search(searchRequest: InvoiceSearchRequest):Observable<InvoiceSearchResponse> {
    return this.httpClient.post<InvoiceSearchResponse>(BASE_URL + 'invoices', searchRequest);
  }
}
