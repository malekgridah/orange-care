package com.billcom.bscs.rest;

import com.billcom.bscs.commons.beans.customer.CreateCustomerRequest;
import com.billcom.bscs.commons.beans.customer.CreateCustomerResponse;
import com.billcom.bscs.commons.beans.customer.overview.CustomerOverviewRequest;
import com.billcom.bscs.commons.beans.customer.overview.CustomerOverviewResponse;
import com.billcom.bscs.commons.beans.customer.search.CustomersSearch;
import com.billcom.bscs.commons.beans.customer.search.CustomersSearchRequest;
import com.billcom.bscs.services.customer.CustomerOverviewService;
import com.billcom.bscs.services.customer.CustomersSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomersSearchService customerContractService;
    private final CustomerOverviewService customerOverviewService;

    @Autowired
    public CustomerController(CustomersSearchService customerContractService,
                              CustomerOverviewService customerOverviewService) {
        this.customerContractService = customerContractService;
        this.customerOverviewService = customerOverviewService;
    }

    @PostMapping("search")
    public @ResponseBody List<CustomersSearch> searchCustomers(@RequestBody CustomersSearchRequest request) {
        return this.customerContractService.searchCustomers(request).getCustomers();
    }

    @PostMapping("overview")
    public @ResponseBody CustomerOverviewResponse overview(@RequestBody CustomerOverviewRequest request) {
        return this.customerOverviewService.overview(request);
    }

    @PostMapping("create")
    public @ResponseBody CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        return null;
    }
}
