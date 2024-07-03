export class CustomersSearch {
    csStatus: string;
    adrLname: string;
    adrFname: string;
    srchCount: number;
    startIndex: number;
    paymentResp: boolean;
    csContrResp: boolean;
    flagCase: boolean;
    flagMatchcode: boolean;
    adrIdno: string;
    csCode: string;
    csIdPub: string;
    resType: string;
    resNo: string;
    includeResHist: boolean;

}

export class CustomersSearchResult {
    csId: string;
    csIdPub: string;
    csCode: string;
    csStatus: number;
    adrLname: number;
    adrFname: boolean;
    adrStreet: boolean;
    adrStreetno: boolean;
    adrZip: boolean;
    adrCity: string;
}

export class CustomerOverview {
    csId: Number;
    customer: Customer;
    contacts: Contract[];
    addresses: Address[];
}

export class Customer {
    csIdPub: String;
    csCode: String;
    csStatus: String;
    csStatusDate: Date;
    csBirthDate: Date;
    csFName: String;
    csLName: String;
    csEmail: String;
    csNationality: String;
    csIdDoc: String;
    csPassword: String;
    csLanguage: String;
    csAddress: String;
    csBillcycle: String;
}

export class Address {
    adrSeq: Number;
    ttlId: Number;
    adrLName: String;
    adrEmail: String;
    adrFame: String;
    adrStreet: String;
    adrCity: String;
    adrZip: String;
    adrBirthDate: Date;
    adrNationality: String;
    countryId: Number;
    docTypeId: Number;
    idNo: String;
}

export class Contract {
    coId: Number;
    dirNum: String;
    coIdPub: String;
    rpCode: Number;
    rateplan: String;
    coStatus: Number;
    coActDate: Date;
}
