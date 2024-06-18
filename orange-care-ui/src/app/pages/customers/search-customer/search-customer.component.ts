import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {Settings} from '../../../app.settings.model';
import {AppSettings} from '../../../app.settings';
import {CustomersService} from '../customers.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CustomersSearch, CustomersSearchResult} from '../customers.model';

@Component({
  selector: 'app-search-customer',
  templateUrl: './search-customer.component.html',
  styleUrls: ['./search-customer.component.scss']
})
export class SearchCustomerComponent implements OnInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  selectForm: FormGroup;
  searchForm: FormGroup;
  optionForm: FormGroup;
  customersSearch: CustomersSearch;

  dataSource = new MatTableDataSource<CustomersSearchResult>([]);
  show = false;
  loading = false;

  public displayedColumns = ['custCode', 'public', 'customer', 'city', 'street', 'status', 'action'];
  public settings: Settings;
  constructor(public appSettings: AppSettings, private customersService: CustomersService, private fb: FormBuilder) {
    this.settings = this.appSettings.settings;
    this.setInitialForm();
  }


  viewFormData() {
    this.dataSource.data = [];
    this.show = true;
    this.loading = true;
    this.customersSearch = new CustomersSearch();

    this.customersSearch.adrFname = this.searchForm.get('adrFname').value;
    this.customersSearch.adrLname = this.searchForm.get('adrLname').value;
    this.customersSearch.adrIdno = this.searchForm.get('adrIdno').value;
    this.customersSearch.csCode = this.searchForm.get('csCode').value;
    this.customersSearch.csIdPub = this.searchForm.get('csIdPub').value;
    this.customersSearch.resNo = this.searchForm.get('resNo').value;

    if (this.selectForm.get('csContrResp').value !== 'all') {
      this.customersSearch.csContrResp = this.selectForm.get('csContrResp').value;
    }

    if (this.selectForm.get('paymentResp').value !== 'all') {
      this.customersSearch.paymentResp = this.selectForm.get('paymentResp').value;
    }

    this.customersSearch.csStatus = this.selectForm.get('csStatus').value;
    this.customersSearch.resType = this.selectForm.get('resType').value;

    this.customersSearch.srchCount = this.optionForm.get('srchCount').value;
    this.customersSearch.flagMatchcode = this.optionForm.get('flagMatchcode').value;
    this.customersSearch.flagCase = this.optionForm.get('flagCase').value;
    this.customersSearch.includeResHist = this.optionForm.get('includeResHist').value;

    this.customersService.search(this.customersSearch).subscribe(res => {
      console.log(res);
      if (res != null) {
        this.dataSource.data = res;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      } else {
        this.dataSource.data = [];
      }
      this.loading = false;
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
      paymentResp: ['all', []],
      csContrResp: ['all', []],
      csStatus: ['a', []],
    });

    this.searchForm = this.fb.group({
      resNo: [null, []],
      adrLname: [null, []],
      adrFname: [null, []],
      adrIdno: [null, []],
      csCode: [null, []],
      csIdPub: [null, []]
    });

    this.optionForm = this.fb.group({
      srchCount: [null, []],
      includeResHist: [false, []],
      flagCase: [false, []],
      flagMatchcode: [true, []],
    });
  }




  ngOnInit() {

  }

}
