package com.billcom.payment.rest;

import com.billcom.payment.commons.beans.*;
import com.billcom.payment.commons.beans.invoices.overview.InvoiceOverviewRequest;
import com.billcom.payment.commons.beans.invoices.search.InvoiceRequest;
import com.billcom.payment.commons.beans.invoices.search.InvoiceResponse;
import com.billcom.payment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class PaymentApiController {

    private final GetInvoicesService getInvoicesService;
    private final PayService payService;
    private final CancelPayService cancelPayService;
    private final TraceLogService traceLogService;
    private final InvoiceOverviewService invoiceOverviewService;

    @Autowired
    public PaymentApiController(GetInvoicesService getInvoicesService,
                                PayService payService,
                                CancelPayService cancelPayService,
                                TraceLogService traceLogService,
                                InvoiceOverviewService invoiceOverviewService) {
        this.getInvoicesService = getInvoicesService;
        this.payService = payService;
        this.cancelPayService = cancelPayService;
        this.traceLogService = traceLogService;
        this.invoiceOverviewService = invoiceOverviewService;
    }


    @PostMapping("invoices")
    public ResponseEntity<InvoiceResponse> invoices(@RequestBody InvoiceRequest request) {
        return ResponseEntity.ok(getInvoicesService.getInvoices(request));
    }

    @PostMapping("InvoiceOverview")
    public void InvoiceOverview(@RequestBody InvoiceOverviewRequest request) {
         ResponseEntity.ok(this.invoiceOverviewService.invoiceOverView(request));
    }

    @PostMapping("pay")
    public ResponseEntity<PayResponse> pay(@RequestHeader(value = "Username", required = false) String username,
                                           @RequestHeader(value = "password", required = false) String password,
                                           @RequestBody PayRequest payRequest) {
        return ResponseEntity.ok(payService.pay(username, password, payRequest));
    }

    @PostMapping("traceLog")
    public ResponseEntity<TraceLogResponse> traceLog(@RequestBody TraceLogRequest traceLogRequest) {
        return ResponseEntity.ok(traceLogService.traceLog(traceLogRequest));
    }

    @PostMapping("cancelPay")
    public ResponseEntity<CancelPayResponse> cancelPay(@RequestHeader(value = "Username") String username,
                                                       @RequestHeader(value = "password") String password,
                                                       @RequestBody CancelPayRequest cancelPayRequest) {
        return ResponseEntity.ok(this.cancelPayService.cancelPay(username,password,cancelPayRequest));
    }
}
