import {AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {Settings} from '../../../../app.settings.model';
import {AppSettings} from '../../../../app.settings';
import {SelectionModel} from '@angular/cdk/collections';
import {Contract} from "../../customers.model";
import {EccodingUriPipe} from "../../../../shared/services/EncodingUri.pipe";
import {ContractsService} from "../../../contracts/contracts.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-contract-customer-tab',
  templateUrl: './contract-customer-tab.component.html',
  styleUrls: ['./contract-customer-tab.component.scss']
})
export class ContractCustomerTabComponent implements OnChanges, AfterViewInit {
  @Input() contracts: Contract[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  public displayedColumns = ['#', 'dirNum', 'coCode', 'rpCode', 'status', 'activationDate', 'action'];

  public dataSource: any;
  public settings: Settings;
  constructor(public appSettings: AppSettings,
              private contractsService: ContractsService,
              private router: Router) {
    console.log("3\n"+this.contracts);
    this.settings = this.appSettings.settings;
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


  ngOnChanges(changes: SimpleChanges) {
    console.log(changes)
    this.dataSource = new MatTableDataSource<Contract>(this.contracts);
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  contractOverview(coId: number, coCode: string) {
    const encodedId = new EccodingUriPipe().transform(coId.toString(), true);
    const encodedCoCode = new EccodingUriPipe().transform(coCode, true);
    this.router.navigate(['contracts', 'overview'], {queryParams:{ contract: encodedCoCode, token:encodedId} }).then((success) => {
      if (success) {
        console.log('Navigation successful!');
      } else {
        console.error('Navigation failed!');
      }
    }).catch((error) => {
      console.error('Error occurred during navigation:', error);
    });
  }

}
