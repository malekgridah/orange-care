package com.billcom.contracts.controller;

import com.billcom.contracts.beans.overview.ContractOverViewRequest;
import com.billcom.contracts.beans.overview.ContractOverviewResponse;
import com.billcom.contracts.beans.search.ContractSearch;
import com.billcom.contracts.beans.search.ContractsSearchRequest;
import com.billcom.contracts.services.ContractOverviewService;
import com.billcom.contracts.services.ContractsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contracts")
public class ContractsController {

    private final ContractsSearchService contractsSearchService;
    private final ContractOverviewService contractOverviewService;

    @Autowired
    public ContractsController(ContractsSearchService contractsSearchService,
                              ContractOverviewService contractOverviewService) {
        this.contractsSearchService = contractsSearchService;
        this.contractOverviewService = contractOverviewService;
    }

    @PostMapping("search")
    public @ResponseBody List<ContractSearch> searchCustomers(@RequestBody ContractsSearchRequest request) {
        return this.contractsSearchService.contractsSearch(request).getContracts();
    }

    @PostMapping("overview")
    public @ResponseBody ContractOverviewResponse overview(@RequestBody ContractOverViewRequest overviewRequest) {
        return this.contractOverviewService.contractOverview(overviewRequest);
    }
}
