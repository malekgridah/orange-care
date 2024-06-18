import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {Settings} from '../../../../app.settings.model';
import {AppSettings} from '../../../../app.settings';
import {Contract, CustomersService} from '../../customers.service';
import {SelectionModel} from '@angular/cdk/collections';

@Component({
  selector: 'app-contract-customer-tab',
  templateUrl: './contract-customer-tab.component.html',
  styleUrls: ['./contract-customer-tab.component.scss']
})
export class ContractCustomerTabComponent implements OnInit , AfterViewInit {
  @Input('customerId') customerId: string;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  public displayedColumns = ['#', 'dirNum', 'coCode', 'rpCode', 'status', 'activationDate', 'action'];

  public dataSource: any;
  public settings: Settings;
  constructor(public appSettings: AppSettings, private customersService: CustomersService) {
    this.settings = this.appSettings.settings;
    this.dataSource = new MatTableDataSource<Contract>(this.customersService.getContractData());
  }

  selection = new SelectionModel<Contract>(true, []);

  /** Whether the number of selected elements matches the total number of rows. */
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


  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

}
