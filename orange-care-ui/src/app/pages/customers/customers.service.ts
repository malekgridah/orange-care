import { Injectable } from '@angular/core';
import {CustomerOverview, CustomersSearch, CustomersSearchResult} from './customers.model';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from "../../../environments/environment";


const BASE_URL = `${environment.apiUrl}/bscs/api/customers`;


@Injectable()
export class CustomersService {
  constructor(private httpClient: HttpClient) { }

  search(searchRequest: CustomersSearch): Observable<CustomersSearchResult[]>  {
      console.log(BASE_URL);
      return this.httpClient.post<CustomersSearchResult[]>(BASE_URL + '/search', {
          csStatus: searchRequest.csStatus,
          adrLname: searchRequest.adrLname,
          adrFname: searchRequest.adrFname,
          srchCount: searchRequest.srchCount,
          startIndex: searchRequest.startIndex,
          paymentResp: searchRequest.paymentResp,
          csContrResp: searchRequest.csContrResp,
          flagCase: searchRequest.flagCase,
          flagMatchcode: searchRequest.flagMatchcode,
          adrIdno: searchRequest.adrIdno,
          csCode: searchRequest.csCode,
          csIdPub: searchRequest.csIdPub,
          resType: searchRequest.resType,
          resNo: searchRequest.resNo,
          includeResHist: searchRequest.includeResHist
      });
  }

  customerOverview(csId):Observable<CustomerOverview> {
      return this.httpClient.post<CustomerOverview>(BASE_URL+'/overview', {csId:csId});
  }
}
