import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {
  BillingAccount,
  Customer,
  Document,
  Invoices,
  InvoiceSearchRequest,
  InvoiceSearchResponse,
  SearchOptions
} from "../invoices.model";
import {InvoiceService} from "../invoice.service";
import {AppSettings} from "../../../app.settings";

@Component({
  selector: 'app-search-invoice',
  templateUrl: './search-invoice.component.html',
  styleUrls: ['./search-invoice.component.scss']
})
export class SearchInvoiceComponent implements OnInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  public displayedColumns = ['documentCode', 'csIdPub', 'billedAmount', 'openAmount', 'dueDate', 'status', 'billingAccountCode', 'action'];


  dataSource = new MatTableDataSource<Invoices>([]);

  appSetting: AppSettings
  searchForm: FormGroup;
  optionForm: FormGroup;
  selectForm: FormGroup;
  filterDateForm: FormGroup;
  showFilter = false;
  loading = false;
  show = false;

  constructor(private fb: FormBuilder,
              private invoiceService: InvoiceService,
              private appSettings: AppSettings) {
    this.appSetting = appSettings;
  }


  setInitialForm() {
    this.selectForm = this.fb.group({
      orderRefDate: [null,[]],
      creditDebit: ['all', []],
      docType: ['IN', []],
      documentStatus: ['all', []],
    });

    this.searchForm = this.fb.group({
      invoiceRef: [null, []],
      cin: [null, []],
      billingAccountCode: [null, []],
      csIdPub: [null, []],
      regNo: [null, []],
    });

    this.filterDateForm = this.fb.group({
      startDate: [null, []],
      endDate: [null, []]
    });

    this.optionForm = this.fb.group({
      srchCount: [null, []],
      flagCase: [false, []],
      flagMatchcode: [true, []],
    });

  }

  searchRequest() {
    let searchRequest: InvoiceSearchRequest = new InvoiceSearchRequest();
    let document: Document = new Document();
    let customer: Customer = new Customer();
    let billingAccount: BillingAccount = new BillingAccount();
    let searchOptions: SearchOptions = new SearchOptions();

    if (this.selectForm.value.orderRefDate != null) {
      searchOptions.orderRefDate = this.selectForm.value.orderRefDate ;
    }

    if (this.selectForm.value.documentType != 'all') {
      searchOptions.documentType = this.selectForm.value.documentType ;
    }

    if (this.selectForm.value.creditDebit != 'all') {
      searchOptions.creditDebit = this.selectForm.value.creditDebit ;
    }

    if (this.selectForm.value.documentStatus != 'all') {
      searchOptions.documentStatus = this.selectForm.value.documentStatus ;
    }

    document.documentCode = this.searchForm.value.invoiceRef;
    customer.csIdPub = this.searchForm.value.csIdPub;
    billingAccount.billingAccountCode = this.searchForm.value.billingAccountCode;

    searchRequest.customer = customer;
    searchRequest.document = document;
    searchRequest.billingAccount = billingAccount;

    searchRequest.cin = this.searchForm.value.cin;
    searchRequest.registryNumber = this.searchForm.value.regNo;
    searchRequest.cin = this.searchForm.value.cin;

    searchRequest.searchCount = this.optionForm.value.srchCount;

    searchRequest.startDate = this.filterDateForm.value.startDate;
    searchRequest.endDate = this.filterDateForm.value.endDate;

    return searchRequest;
  }


  searchInvoices() {
    this.dataSource.data = [];
    this.show = true;
    this.loading = true;
    this.invoiceService.search(this.searchRequest()).subscribe( data => {
      this.dataSource.data  = this.flatData(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    },error => {
      this.loading = false;
      console.log(error)
    });
  }

  ngOnInit() {
    this.setInitialForm();
  }

  private flatData(invoices: InvoiceSearchResponse) {

    let transformedData = [];
    invoices.invoices.forEach(invoice => {
      invoice.customers.forEach(customer => {
        if (customer.billingAccount.billingAccountCode != null) {
          const billingAccount = customer.billingAccount;
          customer.invoices.forEach(inv => {
            transformedData.push({
              customerId: invoice.customerId,
              customerCode: invoice.customerCode,
              billingAccountId: billingAccount.billingAccountId,
              billingAccountCode: billingAccount.billingAccountCode,
              documentId: inv.documentId,
              documentCode: inv.documentCode,
              status: inv.status,
              isPaid: inv.isPaid,
              statusId: inv.statusId,
              isReversed: inv.isReversed,
              billedAmount: `${inv.billedAmount.amount} ${inv.billedAmount.currency}`,
              openAmount: `${inv.openAmount.amount} ${inv.openAmount.currency}`,
              entryDate: inv.entryDate,
              dueDate: inv.dueDate,
              refDate: inv.refDate
            });
          });
        }
      });
    });

    return transformedData;
  }

}
