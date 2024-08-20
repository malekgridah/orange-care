import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Settings} from '../../../app.settings.model';
import {AppSettings} from '../../../app.settings';
import {Router} from '@angular/router';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {ContractsService} from '../contracts.service';
import {ContractsSearchRequest, ContractsSearchResponse, Rateplan} from '../conntracts.model';
import {EccodingUriPipe} from '../../../shared/services/EncodingUri.pipe';

@Component({
  selector: 'app-search-contract',
  templateUrl: './search-contract.component.html',
  styleUrls: ['./search-contract.component.scss']
})
export class SearchContractComponent implements OnInit {
  constructor(public appSettings: AppSettings,
              private contractsService: ContractsService,
              private router: Router,
              private fb: FormBuilder) {
    this.settings = this.appSettings.settings;
    this.setInitialForm();
  }

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  selectForm: FormGroup;
  networksForm: FormGroup;
  searchForm: FormGroup;
  optionForm: FormGroup;
  contractsSearch: ContractsSearchRequest;

  dataSource = new MatTableDataSource<ContractsSearchResponse>([]);
  show = false;
  loading = false;

  public displayedColumns = ['coCode', 'rateplan', 'coStatus', 'csIdPub', 'csCode', 'market', 'network', 'customer', 'street', 'city', 'action'];
  public settings: Settings;


rateplans: Rateplan[];

  searchFormRequest(): ContractsSearchRequest {
    const contractSearch: ContractsSearchRequest = new ContractsSearchRequest();

    contractSearch.srchCount = this.optionForm.value.srchCount;
    contractSearch.flagCase = this.optionForm.value.flagCase;
    contractSearch.includeResHist = this.optionForm.value.includeResHist;

    if (this.selectForm.value.resType !== 'all') {
      contractSearch.resType = this.selectForm.value.resType;
    }
    if (this.selectForm.value.coRpCode !== 'all') {
      contractSearch.coRpCode = this.selectForm.value.coRpCode;
    }
    if (this.selectForm.value.coPaymentOption !== 'all') {
      contractSearch.coPaymentOption = this.selectForm.value.coPaymentOption;
    }
    if (this.selectForm.value.coStatus !== 'all') {
      contractSearch.coStatus = this.selectForm.value.coStatus;
    }

    contractSearch.coCode = this.searchForm.value.coCode;
    contractSearch.resNo = this.searchForm.value.resNo;
    contractSearch.csFName = this.searchForm.value.csFName;
    contractSearch.csLName = this.searchForm.value.csLName;
    contractSearch.csIdPub = this.searchForm.value.csIdPub;
    contractSearch.csCode = this.searchForm.value.csCode;

    if (this.networksForm.value.market !== 'all') {
      contractSearch.market = this.networksForm.value.market;
    }
    if (this.networksForm.value.subMarket !== 'all') {
      contractSearch.subMarket = this.networksForm.value.subMarket;
    }
    if (this.networksForm.value.network !== 'all') {
      contractSearch.network = this.networksForm.value.network;
    }

    return contractSearch;
}

  searchContracts() {
    this.dataSource.data = [];
    this.show = true;
    this.loading = true;
    this.contractsService.search(this.searchFormRequest()).subscribe(res => {
      console.log(res);
      if (res != null) {
        this.dataSource.data = res;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      } else {
        this.dataSource.data = [];
      }
      this.loading = false;
    }, error => {
      this.loading = false;
      console.log(error);
    });
  }


  contractOverview(coId: number, coCode: string) {
    const encodedId = new EccodingUriPipe().transform(coId.toString(), true);
    const encodedCoCode = new EccodingUriPipe().transform(coCode, true);
    this.router.navigate(['contracts', 'overview'], {queryParams: { contract: encodedCoCode, token: encodedId} }).then((success) => {
      if (success) {
        console.log('Navigation successful!');
      } else {
        console.error('Navigation failed!');
      }
    }).catch((error) => {
      console.error('Error occurred during navigation:', error);
    });
  }



  resetForm () {
    this.show = false;
    this.loading = false;
    this.setInitialForm();
    this.dataSource.data = [];
  }

  setInitialForm() {
    this.selectForm = this.fb.group({
      resType: ['dirNum', []],
      coPaymentOption: ['all', []],
      coRpCode: ['all', []],
      coStatus: ['all', []]
    });

    this.searchForm = this.fb.group({
      resNo: [null, []],
      coCode: [null, []],
      csLName: [null, []],
      csFName: [null, []],
      csCode: [null, []],
      csIdPub: [null, []]
    });

    this.networksForm = this.fb.group({
      market: ['all', []],
      subMarket: ['all', []],
      network: ['all', []]
    });

    this.optionForm = this.fb.group({
      srchCount: [null, []],
      flagCase: [false, []],
      includeResHist: [false, []],
    });
  }

  getRateplans() {
    this.contractsService.getRateplans().subscribe(data => {
      console.log(this.rateplans);
      this.rateplans = data.rateplans;
    });
  }

  ngOnInit() {
    this.getRateplans();
  }

}
