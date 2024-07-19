package com.billcom.financials.controller;

import com.billcom.financials.commons.beans.search.TransactionSearchRequest;
import com.billcom.financials.commons.beans.search.TransactionSearchResponse;
import com.billcom.financials.service.TransactionSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/financial")
public class FinancialController {

    private final TransactionSearchService transactionSearchService;

    @Autowired
    public FinancialController(TransactionSearchService transactionSearchService) {
        this.transactionSearchService = transactionSearchService;
    }

    @PostMapping("search")
    public @ResponseBody TransactionSearchResponse search(@RequestBody TransactionSearchRequest request) {
        return this.transactionSearchService.search(request);
    }
}
