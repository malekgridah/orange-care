package com.billcom.bscs.commons;

import com.billcom.bscs.commons.beans.customer.CreateCustomerRequest;
import com.billcom.bscs.commons.beans.customer.CreateCustomerResponse;
import com.billcom.bscs.commons.beans.customer.UpdateCustomerRequest;
import com.billcom.bscs.commons.beans.customer.UpdateCustomerResponse;

public interface CustomerHandler {
    CreateCustomerResponse createCustomer(CreateCustomerRequest request);
    UpdateCustomerResponse updateCustomer(UpdateCustomerRequest request);
}
