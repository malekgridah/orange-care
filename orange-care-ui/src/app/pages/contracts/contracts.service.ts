import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ContractsSearchRequest, ContractsSearchResponse, RateplanResponse} from "./conntracts.model";
import {ContractOverviewRequest, ContractOverviewResponse} from "./contract-overview.model";


const BASE_URL = `${environment.apiUrl}/bscs/api`;
const BASE_CONTRACTS_URL = `${environment.apiUrl}/bscs/api/contracts`;
@Injectable({
  providedIn: 'root'
})
export class ContractsService {

  constructor(private httpClient: HttpClient) { }

  getRateplans():Observable<RateplanResponse> {
    return this.httpClient.post<RateplanResponse>(BASE_URL+'/bscs/getRateplans', {});
  }

  search(searchRequest: ContractsSearchRequest): Observable<ContractsSearchResponse[]>  {
    return this.httpClient.post<ContractsSearchResponse[]>(BASE_CONTRACTS_URL + '/search', searchRequest);
  }

  overview(searchRequest: ContractOverviewRequest): Observable<ContractOverviewResponse>  {
    return this.httpClient.post<ContractOverviewResponse>(BASE_CONTRACTS_URL + '/overview', searchRequest);
  }
}
