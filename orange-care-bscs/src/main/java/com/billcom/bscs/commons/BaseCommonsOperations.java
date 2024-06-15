package com.billcom.bscs.commons;

import com.billcom.bscs.commons.beans.contract.ContractRequest;
import com.billcom.bscs.commons.beans.contract.ContractResponse;
import com.billcom.bscs.commons.beans.customer.*;

public interface BaseCommonsOperations {

    ContractResponse contractRead(ContractRequest contractRequest);
    CustomerResponse customerRead(CustomerResponse customerResponse);
    CustomersSearchResponse searchCustomers(CustomersSearchRequest request);
    CreateCustomerResponse createCustomer(CreateCustomerRequest request);
}
