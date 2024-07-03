package com.billcom.bscs.rest;

import com.billcom.bscs.commons.beans.contract.overview.ContractOverViewRequest;
import com.billcom.bscs.commons.beans.contract.overview.ContractOverviewResponse;
import com.billcom.bscs.commons.beans.contract.search.ContractSearch;
import com.billcom.bscs.commons.beans.contract.search.ContractsSearchRequest;
import com.billcom.bscs.services.contract.ContractOverviewService;
import com.billcom.bscs.services.contract.ContractsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contracts")
public class ContractController {

    private final ContractsSearchService contractsSearchService;
    private final ContractOverviewService contractOverviewService;

    @Autowired
    public ContractController(ContractsSearchService contractsSearchService,
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
