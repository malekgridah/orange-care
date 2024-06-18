import { Injectable } from '@angular/core';
import {CustomersSearch, CustomersSearchResult} from './customers.model';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';


export class EditCustomer {
    fName: string;
    lName: string;
    streetName: string;
    streetNumber: string;
    city: string;
    zip: string;
    state: string;
    county: string;
    country: string;
    currency: string;
    password: string;
    language: string;
    phone: string;
    email: string;
    addressNote: string;
    customerGroup: string;
}

export interface Contract {
  dirNum: string;
  coCode: string;
  rpCode: string;
  status: string;
  activationDate: string;
}

const contractData: Contract[] = [
    {dirNum: '52168629', coCode: 'CONTR0039815090', rpCode: 'Forfait Internet 4G', status: 'd', activationDate: 'Feb 28, 2024'},
    {dirNum: '52184853', coCode: 'CONTR0039815089', rpCode: 'forfait mix 100', status: 'a', activationDate: 'Feb 28, 2024'},
    {dirNum: '52168565', coCode: 'CONTR0039815092', rpCode: 'forfait mix 50', status: 'a', activationDate: 'Feb 28, 2024'},
    {dirNum: '51067371', coCode: 'CONTR0039815844', rpCode: 'forfait mix 50', status: 'o', activationDate: 'Feb 26, 2024'},
    {dirNum: '52169361', coCode: 'CONTR0039815091', rpCode: 'Prepqid mobile', status: 'a', activationDate: 'Feb 28, 2024'},
    {dirNum: '52327645', coCode: 'CONTR0039814518', rpCode: 'forfait mix 20', status: 'a', activationDate: 'Feb 23, 2024'},
    {dirNum: '52182747', coCode: 'CONTR0039815165', rpCode: 'Forfait Internet 4G', status: 's', activationDate: 'Mar 5, 2024'},
    {dirNum: '52168545', coCode: 'CONTR0039815088', rpCode: 'Ghrami Net', status: 'a', activationDate: 'Feb 28, 2024'}
];

const BASE_URL = 'http://localhost:8080/api/customers';


@Injectable()
export class CustomersService {
  constructor(private httpClient: HttpClient) { }

  getContractData() {
    return contractData;
  }

  search(searchRequest: CustomersSearch): Observable<CustomersSearchResult[]>  {
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
}
