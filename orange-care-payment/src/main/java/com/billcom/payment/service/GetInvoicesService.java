package com.billcom.payment.service;

import com.billcom.payment.clients.rest.RestExecutorClient;
import com.billcom.payment.clients.soap.wsi.FinancialDocumentSearchClient;
import com.billcom.payment.commons.beans.*;
import com.billcom.payment.commons.beans.InvoiceRequest;
import com.billcom.payment.commons.beans.Money;
import com.billcom.payment.commons.beans.invoices.search.BillingAccountsByCustomer;
import com.billcom.payment.commons.beans.invoices.search.Invoice;
import com.billcom.payment.commons.beans.invoices.search.InvoiceResponse;
import com.billcom.payment.commons.beans.invoices.search.InvoicesByBillingAccount;
import com.billcom.payment.commons.enums.InvoiceStatus;
import com.billcom.payment.commons.enums.UseCasesIdPub;
import com.billcom.payment.commons.exceptions.DataNotFoundException;
import com.billcom.payment.commons.exceptions.InvokeClientException;
import com.billcom.payment.commons.repositories.bscs.FinTrxInterfaceHistRepository;
import com.billcom.payment.commons.repositories.bscs.FinTrxInterfaceRepository;
import com.billcom.payment.config.properties.SettingsProperties;
import com.billcom.payment.utils.Constants;
import com.ericsson.financialdocumentsearch.*;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class GetInvoicesService {

    private static final Logger logger = LogManager.getLogger(GetInvoicesService.class);

    private final FinancialDocumentSearchClient finDocSearchClient;
    private final RestExecutorClient restExecutorClient;
    private final SettingsProperties settingsProperties;
    private final FinTrxInterfaceRepository finTrxInterfaceRepository;
    private final FinTrxInterfaceHistRepository finTrxInterfaceHistRepository;

    @Autowired
    public GetInvoicesService(FinancialDocumentSearchClient finDocSearchClient,
                              RestExecutorClient restExecutorClient,
                              SettingsProperties settingsProperties,
                              FinTrxInterfaceRepository finTrxInterfaceRepository,
                              FinTrxInterfaceHistRepository finTrxInterfaceHistRepository) {
        this.finDocSearchClient = finDocSearchClient;
        this.restExecutorClient = restExecutorClient;
        this.settingsProperties = settingsProperties;
        this.finTrxInterfaceRepository = finTrxInterfaceRepository;
        this.finTrxInterfaceHistRepository = finTrxInterfaceHistRepository;
    }

    public Boolean checkString(String param) {
        return param != null && !param.isBlank();
    }

    public InvoiceResponse getInvoices(com.billcom.payment.commons.beans.invoices.search.InvoiceRequest request) {

        CustomerDetails customerDetails = null;
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        InvoicesBean invoicesBean = new InvoicesBean();
        String csIdPub = null;
        Long csId = null;

        CustomerReference custRef = new CustomerReference();

        if(request.getStartDate() != null)
            invoicesBean.setStartDate(request.getStartDate().toString());

        if(request.getEndDate() != null)
            invoicesBean.setEndDate(request.getEndDate().toString());

        if (request.getCustomer() != null) {
            csId = request.getCustomer().getCsId();
            csIdPub = request.getCustomer().getCsIdPub();
        }

        if (request.getSearchCount() != null) {
            invoicesBean.setSearchCount(request.getSearchCount());
        }

        if (csId != null || csIdPub != null || request.getDirNum() != null) {
            custRef.setCsId(csId != null ? csId : this.restExecutorClient.getCsIdFromMsisdn(request.getDirNum()));
            custRef.setCsIdPub(csIdPub);
            invoicesBean.setCustRef(custRef);
        }

        if (csId == null && csIdPub == null && request.getDirNum() == null) {
            customerDetails = this.restExecutorClient.getCustomerDetails(null, null, request.getCin(), request.getRegistryNumber());
            logger.info(" +++++ details " + customerDetails);

            if (customerDetails != null && customerDetails.getCustomerId() != null) {
                custRef.setCsId(customerDetails.getCustomerId());
            } else if (customerDetails != null && customerDetails.getCustomerIds() != null && !customerDetails.getCustomerIds().isEmpty()) {

                custRef.setCsIds(customerDetails.getCustomerIds());
                logger.info(" ------------- "+custRef.getCsIds());
            }
            invoicesBean.setCustRef(custRef);
        }

        if (request.getBillingAccount() != null) {
            BillingAccount baRef = new BillingAccount();
            baRef.setBillingAccountCode(request.getBillingAccount().getBillingAccountCode());
            baRef.setBillingAccountId(request.getBillingAccount().getBillingAccountId());
            invoicesBean.setBaRef(baRef);
        }

        if (request.getDocument() != null && request.getDocument().getDocumentCode() != null && !request.getDocument().getDocumentCode().isEmpty()) {
            invoicesBean.setRefFacture(request.getDocument().getDocumentCode());
        }


        if(request.getPrgCodeInclude() != null && request.getPrgCodeInclude().equals(("1"))) {
            if (!this.processPrgCode(this.settingsProperties.getPrgcode().getInclude(),
                    csId, csIdPub, custRef, customerDetails, request.getCin(), request.getRegistryNumber())) {
                invoicesBean.getCustRef().setCsId(0L);
            }
        }

        if(request.getPrgCodeExclude() != null && request.getPrgCodeExclude().equals(("1"))) {
            if (this.processPrgCode(this.settingsProperties.getPrgcode().getExclude(),
                    csId, csIdPub, custRef, customerDetails, request.getCin(), request.getRegistryNumber())) {
                invoicesBean.getCustRef().setCsId(0L);
            }
        }

        invoiceRequest.setInvoicesBean(invoicesBean);
        return this.getInvoices(invoiceRequest);
    }


    private Boolean processPrgCode(List<String> prgCodes, Long csId,
                                   String csIdPub, CustomerReference custRef,
                                   CustomerDetails customerDetails, String cin,
                                   String regno) {

        logger.info("processing prgCode...");
        if (customerDetails == null) {
            customerDetails = this.restExecutorClient.getCustomerDetails(csId != null ? csId : custRef.getCsId(),
                    csIdPub != null ? csIdPub : custRef.getCsIdPub(), cin, regno);
            logger.error("details is null");
        }

        for (String c : prgCodes) {
            if (c.equals(customerDetails.getPrgcode())) {
                return true;
            }
        }

        return false;
    }

    private InvoiceResponse getInvoices(InvoiceRequest invoiceRequest) {
        InputAttributes inputAttributes = new InputAttributes();
        DocTypesRequest docTypesRequest = new DocTypesRequest();
        docTypesRequest.getDocType().add("IN");
        inputAttributes.setDocTypes(docTypesRequest);

        if (invoiceRequest.getInvoicesBean().getSearchCount() == null && this.settingsProperties.getDocumentSearchCount() != null) {
            inputAttributes.setResultLimit(BigInteger.valueOf(Long
                    .parseLong(this.settingsProperties.getDocumentSearchCount())));
        }

        try {
            if(invoiceRequest.getInvoicesBean() != null) {
                InvoicesBean invoicesBean = invoiceRequest.getInvoicesBean();
                if(this.checkString(invoicesBean.getRefFacture())) {
                    inputAttributes.setDocumentCode(invoicesBean.getRefFacture());
                }

                if(invoicesBean.getBaRef() != null) {
                    inputAttributes.setBillingAccountId(invoicesBean.getBaRef().getBillingAccountId());
                    inputAttributes.setBillingAccountCode(invoicesBean.getBaRef().getBillingAccountCode());
                }

                if(invoicesBean.getCustRef() != null) {
                    inputAttributes.setCsId(invoicesBean.getCustRef().getCsId());
                    inputAttributes.setCsIdPub(invoicesBean.getCustRef().getCsIdPub());
                }

                if(this.checkString(invoicesBean.getStartDate())){
                    inputAttributes.setDueDateFrom(DatatypeFactory
                            .newInstance().newXMLGregorianCalendar(invoicesBean.getStartDate()));
                }

                if(this.checkString(invoicesBean.getEndDate())){
                    inputAttributes.setDueDateUntil(DatatypeFactory
                            .newInstance().newXMLGregorianCalendar(invoicesBean.getEndDate()));
                }

                if (invoicesBean.getSearchCount() != null) {
                    inputAttributes.setResultLimit(BigInteger.valueOf(invoicesBean.getSearchCount()));
                }

                if(invoicesBean.getCustRef() != null
                        && !this.checkString(invoicesBean.getCustRef().getCsIdPub())
                        && invoicesBean.getCustRef().getCsId() == null
                        && invoicesBean.getCustRef().getCsIds() != null
                        && !invoicesBean.getCustRef().getCsIds().isEmpty()) {
                    List<DocumentsListpartResponse> listPartResponses = new ArrayList<>();
                    for ( Long csId : invoicesBean.getCustRef().getCsIds()) {
                        inputAttributes.setCsId(csId);
                        listPartResponses.addAll(this.documentSearch(inputAttributes));
                    }
                    return this.prepareInvoicesResponse(listPartResponses);
                } else {
                    return this.prepareInvoicesResponse(this.documentSearch(inputAttributes));
                }
            }

        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }

        return new InvoiceResponse();
    }

    private List<DocumentsListpartResponse> documentSearch(InputAttributes inputAttributes) {
        FinancialDocumentSearchRequest searchRequest = new FinancialDocumentSearchRequest();
        searchRequest.setInputAttributes(inputAttributes);
        FinancialDocumentSearchResponse searchResponse;
        try {
            searchResponse = this.finDocSearchClient.execute(searchRequest);

            if(searchResponse.isSearchIsComplete() && (searchResponse.getDocuments() == null ||
                    searchResponse.getDocuments().getItem() == null ||
                    searchResponse.getDocuments().getItem().isEmpty()) ) {
                throw new DataNotFoundException("No document found");
            }
        }catch (SOAPFaultException soapFaultException) {
            throw new InvokeClientException(soapFaultException);
        }

        return searchResponse.getDocuments().getItem();
    }


    private InvoiceResponse prepareInvoicesResponse(List<DocumentsListpartResponse> documentsList) {
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        List<BillingAccountsByCustomer> byCustomerList = new ArrayList<>();

//        Map<Long, List<DocumentsListpartResponse>> groupByBillAccId = documentsList
//                .stream()
//                .collect(Collectors.groupingBy(documentsListpartResponse -> documentsListpartResponse
//                        .getBillingAccountId() != null ? documentsListpartResponse.getBillingAccountId() : 0L));

        Map<Long, Map<Long, List<DocumentsListpartResponse>>> groupedByCustomerAndAccount = documentsList
                .stream()
                .collect(Collectors.groupingBy(
                        DocumentsListpartResponse::getCsId,
                        Collectors.groupingBy(documentsListpartResponse -> documentsListpartResponse
                        .getBillingAccountId() != null ? documentsListpartResponse.getBillingAccountId() : 0L)
                ));

        AtomicInteger i = new AtomicInteger();
        groupedByCustomerAndAccount.forEach((customerId, accounts) -> {
            i.getAndIncrement();
            System.out.println(i+" CustomerId: " + customerId);
            BillingAccountsByCustomer billingAccountsByCustomer = new BillingAccountsByCustomer();
            billingAccountsByCustomer.setCustomerId(customerId);

            List<InvoicesByBillingAccount> billingAccounts = new ArrayList<>();

            accounts.forEach((accountId, accountInvoices) -> {
                InvoicesByBillingAccount invoicesByBillingAccount = new InvoicesByBillingAccount();
                BillingAccount billingAccount = new BillingAccount();
                billingAccount.setBillingAccountId(accountId);

                System.out.println("  BillingAccountId: " + accountId);
                List<Invoice> invoices = new ArrayList<>();
                accountInvoices.forEach(invoice -> {
                    billingAccount.setBillingAccountCode(invoice.getBillingAccountCode());
                    billingAccountsByCustomer.setCustomerCode(invoice.getCsCode());

                    Invoice invoiceOut = new Invoice();
                    Money money = new Money();
                    money.setAmount(invoice.getDocumentAmountDoc() != null ? invoice.getDocumentAmountDoc().getAmount() : null);
                    money.setCurrency(invoice.getDocumentAmountDoc() != null ? invoice.getDocumentAmountDoc().getCurrency() : null);
                    invoiceOut.setBilledAmount(money);
                    if (invoice.getDueDate() != null) {
                        invoiceOut.setDueDate(invoice.getDueDate().toGregorianCalendar().toZonedDateTime().toLocalDate());
                    }

                    if (invoice.getRefDate() != null) {
                        invoiceOut.setRefDate(invoice.getRefDate().toGregorianCalendar().toZonedDateTime().toLocalDate());
                    }

                    if (invoice.getEntryDate() != null) {
                        invoiceOut.setEntryDate(invoice.getEntryDate().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
                    }
                    invoiceOut.setDocumentId(invoice.getDocumentId());
                    invoiceOut.setDocumentCode(invoice.getDocumentCode());
                    invoiceOut.setIsPaid(invoice.getOpenAmountDoc().getAmount() == 0);
//                    invoiceOut.setStatus(this.getInvoiceStatus(invoice));
//                    invoiceOut.setStatusId(InvoiceStatus.valueOf(invoiceOut.getStatus()).getStatusId());
                    invoiceOut.setIsReversed(invoice.isReversed());
                    money.setAmount(invoice.getOpenAmountDoc().getAmount());
                    invoiceOut.setOpenAmount(money);
                    invoices.add(invoiceOut);
                    System.out.println("    InvoiceId: " + invoice.getDocumentId() + " DocumentCode: " + invoice.getDocumentCode());
                });
                invoicesByBillingAccount.setBillingAccount(billingAccount);
                invoicesByBillingAccount.setInvoices(invoices);
                billingAccounts.add(invoicesByBillingAccount);
                billingAccountsByCustomer.setCustomers(billingAccounts);
            });
            byCustomerList.add(billingAccountsByCustomer);
        });

        invoiceResponse.setInvoices(byCustomerList);
        invoiceResponse.setIsSuccessful(true);
        invoiceResponse.setComment("Success");

        return invoiceResponse;
    }


    private String getInvoiceStatus(DocumentsListpartResponse document) {
        String status = null;
        if (document.getOpenAmountDoc() != null) {
            status = document.getOpenAmountDoc().getAmount() > 0 ? InvoiceStatus.Not_Paid.getStatus() : InvoiceStatus.Paid.getStatus();
            if (!status.equalsIgnoreCase(Constants.PAID)) {
                if (this.finTrxInterfaceHistRepository.existsByDocumentIdAndCsIdAndUseCaseCode(document.getDocumentId(), document.getCsId(), UseCasesIdPub.PAYMENT.getValue()) ||
                        this.finTrxInterfaceRepository.existsByDocumentIdAndCsIdAndUseCaseCode(document.getDocumentId(), document.getCsId(), UseCasesIdPub.PAYMENT.getValue())) {
                    return InvoiceStatus.IN_Progress.getStatus();
                }
            }
        }
        return status;
    }

}

