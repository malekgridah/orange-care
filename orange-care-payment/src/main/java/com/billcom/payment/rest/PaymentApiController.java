package com.billcom.payment.rest;

import com.billcom.payment.commons.beans.*;
import com.billcom.payment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin("*")
public class PaymentApiController {

    private final GetInvoicesService getInvoicesService;
    private final GetInvoicesV2 getInvoicesV2;
    private final PayService payService;
    private final CancelPayService cancelPayService;
    private final TraceLogService traceLogService;

    @Autowired
    public PaymentApiController(GetInvoicesService getInvoicesService, GetInvoicesV2 getInvoicesV2,
                                PayService payService,
                                CancelPayService cancelPayService,
                                TraceLogService traceLogService) {
        this.getInvoicesService = getInvoicesService;
        this.getInvoicesV2 = getInvoicesV2;
        this.payService = payService;
        this.cancelPayService = cancelPayService;
        this.traceLogService = traceLogService;
    }


    @GetMapping("invoices")
    public ResponseEntity<com.billcom.payment.commons.beans.invoices.InvoiceResponse> invoices(@RequestParam(value = "startDate", required = false) String startDate,
                                                                                               @RequestParam(value = "endDate", required = false) String endDate,
                                                                                               @RequestParam(value = "csId", required = false) Long csId,
                                                                                               @RequestParam(value = "csIdPub", required = false) String csIdPub,
                                                                                               @RequestParam(value = "billingAccountId", required = false) Long billingAccountId,
                                                                                               @RequestParam(value = "billingAccountCode", required = false) String billingAccountCode,
                                                                                               @RequestParam(value = "msisdn", required = false) String msisdn,
                                                                                               @RequestParam(value = "cin", required = false) String cin,
                                                                                               @RequestParam(value = "regNo", required = false) String regNo,
                                                                                               @RequestParam(value = "refFacture", required = false) String refFacture,
                                                                                               @RequestParam(value = "prgCodeInclude", required = false) String prgCodeInclude,
                                                                                               @RequestParam(value = "prgCodeExclude", required = false) String prgCodeExclude) {
        return ResponseEntity.ok(getInvoicesV2.getInvoices(csId, csIdPub,
                msisdn, cin,
                billingAccountId, billingAccountCode,
                regNo, prgCodeInclude,
                prgCodeExclude, startDate,
                endDate, refFacture));
    }

    @PostMapping("pay")
    public ResponseEntity<PayResponse> pay(@RequestHeader(value = "Username", required = false) String username,
                                           @RequestHeader(value = "password", required = false) String password,
                                           @RequestBody PayRequest payRequest) {
        return ResponseEntity.ok(payService.pay(username, password, payRequest));
    }

    @GetMapping("getInvoices")
    public ResponseEntity<InvoiceResponse> getInvoices(@RequestParam(value = "startDate", required = false) String startDate,
                                                       @RequestParam(value = "endDate", required = false) String endDate,
                                                       @RequestParam(value = "csId", required = false) Long csId,
                                                       @RequestParam(value = "csIdPub", required = false) String csIdPub,
                                                       @RequestParam(value = "billingAccountId", required = false) Long billingAccountId,
                                                       @RequestParam(value = "billingAccountCode", required = false) String billingAccountCode,
                                                       @RequestParam(value = "msisdn", required = false) String msisdn,
                                                       @RequestParam(value = "cin", required = false) String cin,
                                                       @RequestParam(value = "regNo", required = false) String regNo,
                                                       @RequestParam(value = "refFacture", required = false) String refFacture,
                                                       @RequestParam(value = "prgCodeInclude", required = false) String prgCodeInclude,
                                                       @RequestParam(value = "prgCodeExclude", required = false) String prgCodeExclude) {
        return ResponseEntity.ok(this.getInvoicesService.getInvoices(csId, csIdPub,
                msisdn, cin,
                billingAccountId, billingAccountCode,
                regNo, prgCodeInclude,
                prgCodeExclude, startDate,
                endDate, refFacture));
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
