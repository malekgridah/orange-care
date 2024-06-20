//package com.billcom.payment.soap;
//
//import com.billcom.payment.commons.beans.*;
//import com.billcom.payment.service.TraceLogService;
//import com.billcom.payment.service.GetInvoicesService;
//import com.billcom.payment.service.PayService;
//import jakarta.jws.WebMethod;
//import jakarta.jws.WebParam;
//import jakarta.jws.WebResult;
//import jakarta.jws.WebService;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Log4j2
//@Component
//@WebService
//public class PaymentApi {
//
//    private final PayService payService;
//    private final TraceLogService traceLogService;
//    private final GetInvoicesService getInvoicesService;
//
//    @Autowired
//    public PaymentApi(PayService payService,
//                      TraceLogService traceLogService,
//                      GetInvoicesService getInvoicesService) {
//
//        this.payService = payService;
//        this.traceLogService = traceLogService;
//        this.getInvoicesService = getInvoicesService;
//    }
//
//    @WebMethod(operationName = "traceLog")
//    public @WebResult TraceLogResponse traceLog(@WebParam TraceLogRequest traceLogRequest) {
//        return this.traceLogService.traceLog(traceLogRequest);
//    }
//
//    @WebMethod(operationName = "pay")
//    public PayResponse pay(@WebParam String username,
//                           @WebParam String password,
//                           @WebParam PayRequest request) {
//        try {
//            return this.payService.pay(username, password, request);
//        }catch (RuntimeException runtimeException) {
//            log.error("error ",runtimeException);
//            PayResponse payResponse = new PayResponse();
//            payResponse.setErrorCode("");
//            payResponse.setIsSuccessful(false);
//            payResponse.setComment(runtimeException.getMessage());
//            return payResponse;
//        }
//    }
//
//    @WebMethod(operationName = "getInvoices")
//    public InvoiceResponse getInvoices(@WebParam(name = "csId") Long csId,
//                                       @WebParam(name = "csIdPub") String csIdPub,
//                                       @WebParam(name = "msisdn") String msisdn,
//                                       @WebParam(name = "cin") String cin,
//                                       @WebParam(name = "billingAccountId") Long billingAccountId,
//                                       @WebParam(name = "billingAccountCode") String billingAccountCode,
//                                       @WebParam(name = "regNo") String regNo,
//                                       @WebParam(name = "prgCodeInclude") String prgCodeInclude,
//                                       @WebParam(name = "prgCodeExclude") String prgCodeExclude,
//                                       @WebParam(name = "startDate") String startDate,
//                                       @WebParam(name = "endDate") String endDate,
//                                       @WebParam(name = "refFacture") String refFacture) {
//        try {
//            return this.getInvoicesService.getInvoices(csId, csIdPub,
//                    msisdn, cin,
//                    billingAccountId, billingAccountCode,
//                    regNo, prgCodeInclude,
//                    prgCodeExclude, startDate,
//                    endDate, refFacture);
//        }catch (RuntimeException runtimeException) {
//            log.error("error ",runtimeException);
//            InvoiceResponse invoiceResponse = new InvoiceResponse();
//            invoiceResponse.setErrorCode("");
//            invoiceResponse.setIsSuccessful(false);
//            invoiceResponse.setComment(runtimeException.getMessage());
//            invoiceResponse.setCustomerRef(null);
//            invoiceResponse.setCustomerRef(null);
//            return invoiceResponse;
//        }
//    }
//}
