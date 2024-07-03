export class Rateplan {
    rpCode: number;
    rpCodePub: string;
    rpDes: string;
    rpOcc: boolean;
    scope: number;
}

export class RateplanResponse {
    rateplans: Rateplan[]
}


export class ContractsSearchRequest {
    coStatus: string;
    resType: string;
    coRpCode: string;
    coPaymentOption: string;
    resNo: string;
    coCode: string;
    csLName: string;
    csFName: string;
    csCode: string;
    csIdPub: string;
    market: string;
    subMarket: string;
    network: string;
    srchCount: number;
    flagCase: boolean;
    includeResHist: boolean;
}

export class ContractsSearchResponse {
    coId: number;
    coIdPub: string;
    csCode: string;
    publicKey: string;
    status: number;
    subMarket: string;
    subMarketId: number;
    rateplan: string;
    rpCode: number;
    homeNetwork: string;
    customer: string;
    street: string;
    city: string;
    resType: string;
    resNo: string;
}
