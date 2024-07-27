package com.billcom.financials.controller;

import com.billcom.financials.commons.beans.document.DocumentDetailsRequest;
import com.billcom.financials.commons.beans.document.DocumentDetailsResponse;
import com.billcom.financials.commons.beans.overview.TransactionOverviewRequest;
import com.billcom.financials.commons.beans.overview.TransactionOverviewResponse;
import com.billcom.financials.commons.beans.search.TransactionSearchRequest;
import com.billcom.financials.commons.beans.search.TransactionSearchResponse;
import com.billcom.financials.service.DocumentDetailsService;
import com.billcom.financials.service.TransactionOverviewService;
import com.billcom.financials.service.TransactionSearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/financial")
@SecurityRequirement(name = "bearerScheme")
public class FinancialController {

    private final TransactionSearchService transactionSearchService;
    private final TransactionOverviewService transactionOverviewService;
    private final DocumentDetailsService documentDetailsService;

    @Autowired
    public FinancialController(TransactionSearchService transactionSearchService,
                               TransactionOverviewService transactionOverviewService,
                               DocumentDetailsService documentDetailsService) {
        this.transactionSearchService = transactionSearchService;
        this.transactionOverviewService = transactionOverviewService;
        this.documentDetailsService = documentDetailsService;
    }

    @PostMapping("search")
    public ResponseEntity<TransactionSearchResponse> search(@RequestBody TransactionSearchRequest request) {
        return ResponseEntity.ok(this.transactionSearchService.search(request));
    }

    @PostMapping("overview")
    public ResponseEntity<TransactionOverviewResponse> overview(@RequestBody TransactionOverviewRequest request) {
        return ResponseEntity.ok(this.transactionOverviewService.overview(request));
    }

    @PostMapping("documentDetails")
    public ResponseEntity<DocumentDetailsResponse> documentDetails(@RequestBody DocumentDetailsRequest request) {
        return ResponseEntity.ok(this.documentDetailsService.documentDetails(request));
    }
}
