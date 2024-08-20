package com.billcom.contracts.controller;

import com.billcom.contracts.beans.create.ContractCreateRequest;
import com.billcom.contracts.beans.create.ContractCreateResponse;
import com.billcom.contracts.beans.overview.ContractOverViewRequest;
import com.billcom.contracts.beans.overview.ContractOverviewResponse;
import com.billcom.contracts.beans.search.ContractSearch;
import com.billcom.contracts.beans.search.ContractsSearchRequest;
import com.billcom.contracts.services.ContractOverviewService;
import com.billcom.contracts.services.ContractsSearchService;
import com.billcom.contracts.services.CreateContractService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerScheme")
@RequestMapping("api/contracts")
public class ContractsController {

    private final ContractsSearchService contractsSearchService;
    private final ContractOverviewService contractOverviewService;
    private final CreateContractService createContractService;

    @Autowired
    public ContractsController(ContractsSearchService contractsSearchService,
                               ContractOverviewService contractOverviewService,
                               CreateContractService createContractService) {
        this.contractsSearchService = contractsSearchService;
        this.contractOverviewService = contractOverviewService;
        this.createContractService = createContractService;
    }

    @PostMapping("search")
    public @ResponseBody List<ContractSearch> searchCustomers(@RequestBody ContractsSearchRequest request) {
        return this.contractsSearchService.contractsSearch(request).getContracts();
    }

    @PostMapping("overview")
    public @ResponseBody ContractOverviewResponse overview(@RequestBody ContractOverViewRequest overviewRequest) {
        return this.contractOverviewService.contractOverview(overviewRequest);
    }

    @PostMapping("create")
    public @ResponseBody ContractCreateResponse create(@RequestBody ContractCreateRequest request) {
        return this.createContractService.createContract(request);
    }
}
