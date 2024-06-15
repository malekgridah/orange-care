package com.billcom.bscs.rest;

import com.billcom.bscs.commons.beans.customer.CreateCustomerRequest;
import com.billcom.bscs.commons.beans.customer.CreateCustomerResponse;
import com.billcom.bscs.commons.beans.customer.CustomersSearch;
import com.billcom.bscs.commons.beans.customer.CustomersSearchRequest;
import com.billcom.bscs.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService customerContractService;

    @Autowired
    public CustomerController(CustomerService customerContractService) {
        this.customerContractService = customerContractService;
    }

    @PostMapping("search")
    public @ResponseBody List<CustomersSearch> searchCustomers(@RequestBody CustomersSearchRequest request) {
        return this.customerContractService.searchCustomers(request).getCustomers();
    }

    @PostMapping("create")
    public @ResponseBody CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        return null;
    }
}
