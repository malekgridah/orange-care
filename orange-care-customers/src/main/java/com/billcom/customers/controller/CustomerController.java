package com.billcom.customers.controller;

import com.billcom.customers.beans.create.CreateCustomerRequest;
import com.billcom.customers.beans.create.CreateCustomerResponse;
import com.billcom.customers.beans.overview.CustomerOverviewRequest;
import com.billcom.customers.beans.overview.CustomerOverviewResponse;
import com.billcom.customers.beans.search.CustomersSearch;
import com.billcom.customers.beans.search.CustomersSearchRequest;
import com.billcom.customers.services.CreateCustomerService;
import com.billcom.customers.services.CustomerOverviewService;
import com.billcom.customers.services.CustomersSearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerScheme")
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomersSearchService customerContractService;
    private final CustomerOverviewService customerOverviewService;
    private final CreateCustomerService createCustomerService;

    @Autowired
    public CustomerController(CustomersSearchService customerContractService,
                              CustomerOverviewService customerOverviewService,
                              CreateCustomerService createCustomerService) {
        this.customerContractService = customerContractService;
        this.customerOverviewService = customerOverviewService;
        this.createCustomerService = createCustomerService;
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
    public @ResponseBody CreateCustomerResponse create(@RequestBody CreateCustomerRequest request) {
        return this.createCustomerService.create(request);
    }
}
