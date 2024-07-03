export interface ContractServiceNode {
    service: string;
    value?: string;
    status?:string;
    validFrom?:Date;
    pendingStatus?:string;
    oneTimeCharge?:number;
    recurringCharge?:number;
    paymentOption?:string;
    resource?:string;
    children?: ContractServiceNode[];
}

export interface ContractServiceFlatNode {
    expandable: boolean;
    service: string;
    value: string;
    status: string;
    validFrom: Date;
    pendingStatus:string;
    oneTimeCharge:number;
    recurringCharge:number;
    paymentOption:string;
    resource:string;
    level: number;
}

export class ContractOverviewRequest{
    coId: number;
    coCode: string;
}

export class ContractOverviewResponse{
    coId: number;
    contract: Contract;
}

export class Contract {
    coId: number;
    coCode: string;
    csId: number;
    csIdPub: string;
    scCode: number;
    scCodePub: string;
    subMarket: number;
    subMarketIdPub: string;

    coStatus: number;
    coLastReason: number;
    coLastReasonShdes: string;
    reason: number;
    reasonShdes: string;

    coPendingDate: Date;
    coLastStatusChangeDate: Date;
    coModDate: Date;
    coEntDate: Date;
    coActivatedDate: Date;
    coSignedDate: Date;

    contractServiceNode : ContractServiceNode[];
}
