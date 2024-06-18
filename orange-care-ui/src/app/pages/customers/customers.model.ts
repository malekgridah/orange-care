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
