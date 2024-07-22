package com.billcom.customers.controller;

import com.billcom.customers.beans.overview.CustomerOverviewRequest;
import com.billcom.customers.beans.overview.CustomerOverviewResponse;
import com.billcom.customers.beans.search.CustomersSearch;
import com.billcom.customers.beans.search.CustomersSearchRequest;
import com.billcom.customers.services.CustomerOverviewService;
import com.billcom.customers.services.CustomersSearchService;
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
}
