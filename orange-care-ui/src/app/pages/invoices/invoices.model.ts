export class InvoiceSearchRequest {
    document: Document;
    customer: Customer;
    billingAccount: BillingAccount;
    dirNum: string;
    cin: string;
    registryNumber: string;
    searchCount: number;
    startDate: Date;
    endDate: Date;
    prgCodeInclude: string;
    prgCodeExclude: string;
    searchOptions: SearchOptions;
}

export class Document {
    documentId: number;
    documentCode: string;
}

export class Customer {
    csId: number;
    csIdPub: string;
}

export class BillingAccount {
    billingAccountId: number;
    billingAccountCode: string;
}

export class SearchOptions {
    creditDebit: string;
    orderRefDate: string;
    documentStatus: string;
    documentType: string;
}
export class OperationResponse {
    errorCode: string;
    comment: string;
    isSuccessful: boolean;
}

export class billingAccount {
    billingAccountId: number;
    billingAccountCode: string;
}

export class Money {
    currency: string;
    amount: number;
}

export class Invoice {
    documentId: number;
    documentCode: string;
    status: string;
    statusId: string;
    isPaid: boolean;
    isReversed: boolean;
    billedAmount: Money;
    openAmount: Money;
    entryDate: Date;
    dueDate: Date;
    refDate: Date;
}

export class InvoicesByBillingAccount {
    billingAccount: BillingAccount;
    invoices: Invoice[];
}

export class Invoices {
    customerId: number;
    customerCode: string;
    customers: InvoicesByBillingAccount[];
}


export class InvoiceSearchResponse extends OperationResponse {
    invoices: Invoices[];
}


