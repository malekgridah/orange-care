import {AfterViewInit, Component, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";
import {Contract} from "../../../customers.model";

@Component({
  selector: 'app-customer-billing-accounts',
  templateUrl: './customer-billing-accounts.component.html',
  styleUrls: ['./customer-billing-accounts.component.scss']
})
export class CustomerBillingAccountsComponent implements OnChanges, AfterViewInit {

  @ViewChild(MatPaginator) paginatorr: MatPaginator;
  public dataSource: any = new MatTableDataSource<any>(['hello','','','','','','','','','','','','','','','']);

  public displayedColumns = ['#', 'csCode', 'billingAccount', 'currentBalance','previousBalance', 'splitBilling', 'billMedium', 'paymentMethod', 'status', 'action'];

  constructor() { }

  selection = new SelectionModel<any>(true, []);

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
        this.selection.clear() :
        this.dataSource.data.forEach(row => this.selection.select(row));
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: Contract): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.dirNum}`;
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginatorr;
  }

}
