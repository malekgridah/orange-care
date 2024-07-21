import { Component, OnInit  } from '@angular/core';
import {FlatTreeControl} from "@angular/cdk/tree";
import {MatTreeFlatDataSource, MatTreeFlattener} from "@angular/material/tree";
import {
  Contract,
  ContractOverviewRequest,
  ContractOverviewResponse,
  ContractServiceFlatNode,
  ContractServiceNode
} from "../contract-overview.model";
import {EccodingUriPipe} from "../../../shared/services/EncodingUri.pipe";
import {ActivatedRoute} from "@angular/router";
import {ContractsService} from "../contracts.service";
import {AppSettings} from "../../../app.settings";

@Component({
  selector: 'app-contract-overview',
  templateUrl: './contract-overview.component.html',
  styleUrls: ['./contract-overview.component.scss']
})
export class ContractOverviewComponent implements OnInit {
  displayedColumns: string[] = ['name', 'value', 'status', 'validFrom', 'pendingStatus','oneTimeCharge', 'recurringCharge', 'paymentOption', 'resources'];

  contract: Contract;
  expanded = false;
  protected appSettings: any;
  mainDirNum = '--';

  coCode: string;
  constructor(private route:ActivatedRoute,
              private contractService: ContractsService,
              private appSetting: AppSettings) {
    this.appSettings = appSetting;
  }

  getNameAvatar(firstName: string, lastName: string): string {
    return firstName.charAt(0).toUpperCase() + lastName.charAt(0).toUpperCase();
  }

  ngOnInit() {
    this.getCustomerOverview();
  }

  getCustomerOverview() {
    this.route.queryParams.subscribe(param => {
      let id = param['token'];
      this.coCode = new EccodingUriPipe().transform(param['contract'],false);
      let contractOverview: ContractOverviewRequest = new ContractOverviewRequest();
      console.log(this.coCode)
      contractOverview.coCode = this.coCode;
      this.contractService.overview(contractOverview)
          .subscribe(data => {
            console.log(data);
            this.dataSource.data = data.contract.contractServiceNode;
            this.contract = data.contract;
            this.mainDirNum = this.getMainDirNum();
            console.log(this.mainDirNum)
          })
    });
  }


  private _transformer = (node: ContractServiceNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      service: node.service,
      value: node.value,
      status: node.status,
      validFrom: node.validFrom,
      pendingStatus: node.pendingStatus,
      oneTimeCharge: node.oneTimeCharge,
      recurringCharge: node.recurringCharge,
      paymentOption: node.paymentOption,
      resource: node.resource,
      level: level
    };
  }

  treeControl = new FlatTreeControl<ContractServiceFlatNode>(
      node => node.level, node => node.expandable);

  treeFlattener = new MatTreeFlattener(
      this._transformer, node => node.level, node => node.expandable, node => node.children);

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);
  hasChild = (_: number, node: ContractServiceFlatNode) => node.expandable;

  getMainDirNum() {
    return this.contract.resources.dirNums.filter(entry => entry.mainDirNum)
        .map(entry => entry.dirNum)
        .pop();
  }

  getDnStatusDes() {
    this.contract.resources.dirNums = this.contract.resources.dirNums.map(bn => {
      switch(bn.dnStatus) {
        case 'f': bn.dnStatusDes='free'; break;
        case 'p': bn.dnStatusDes='purchased'; break;
        case 'v': bn.dnStatusDes='received'; break;
        case 'r': bn.dnStatusDes='reserved'; break;
        case 'a': bn.dnStatusDes='assigned'; break;
        case 'd': bn.dnStatusDes='De-Assigned'; break;
        case 'i': bn.dnStatusDes='ported in'; break;
        case 'o': bn.dnStatusDes='ported out'; break;
        case 's': bn.dnStatusDes='snapped back out'; break;
        case 't': bn.dnStatusDes='snapped back out reserved'; break;
        case 'b': bn.dnStatusDes='pre-active'; break;
        case 'w': bn.dnStatusDes='ordered'; break;
        case 'c': bn.dnStatusDes='ported in and contracted'; break;
        case 'l': bn.dnStatusDes='failed'; break;
        case 'x': bn.dnStatusDes='member of pool'; break;
        case 'y': bn.dnStatusDes='ported out special'; break;
        case 'z': bn.dnStatusDes='booked'; break;
      }
      return bn;
    })
  }
}



const TREE_DATA: ContractServiceNode[] = [];
