package com.billcom.payment.service;

import com.billcom.payment.clients.rest.RestExecutorClient;
import com.billcom.payment.clients.soap.wsi.FinancialDocumentSearchClient;
import com.billcom.payment.commons.beans.*;
import com.billcom.payment.commons.dtos.bscs.OrderhdrAllDto;
import com.billcom.payment.commons.enums.UseCasesIdPub;
import com.billcom.payment.commons.exceptions.DataNotFoundException;
import com.billcom.payment.commons.exceptions.InvokeClientException;
import com.billcom.payment.commons.mappers.bscs.OrderhdrAllMapper;
import com.billcom.payment.commons.repositories.bscs.FinTrxInterfaceHistRepository;
import com.billcom.payment.commons.repositories.bscs.FinTrxInterfaceRepository;
import com.billcom.payment.commons.repositories.bscs.OrderhdrAllRepository;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetInvoicesService {

    private static final Logger logger = LogManager.getLogger(GetInvoicesService.class);

    private final FinancialDocumentSearchClient finDocSearchClient;
    private final RestExecutorClient restExecutorClient;
    private final SettingsProperties settingsProperties;
    private final OrderhdrAllRepository orderhdrAllRepository;
    private final OrderhdrAllMapper orderhdrAllMapper;
    private final FinTrxInterfaceRepository finTrxInterfaceRepository;
    private final FinTrxInterfaceHistRepository finTrxInterfaceHistRepository;

    @Autowired
    public GetInvoicesService(FinancialDocumentSearchClient finDocSearchClient,
                              RestExecutorClient restExecutorClient, SettingsProperties settingsProperties,
                              OrderhdrAllRepository orderhdrAllRepository,
                              OrderhdrAllMapper orderhdrAllMapper, FinTrxInterfaceRepository finTrxInterfaceRepository, FinTrxInterfaceHistRepository finTrxInterfaceHistRepository) {
        this.finDocSearchClient = finDocSearchClient;
        this.restExecutorClient = restExecutorClient;
        this.settingsProperties = settingsProperties;
        this.orderhdrAllRepository = orderhdrAllRepository;
        this.orderhdrAllMapper = orderhdrAllMapper;
        this.finTrxInterfaceRepository = finTrxInterfaceRepository;
        this.finTrxInterfaceHistRepository = finTrxInterfaceHistRepository;
    }

    public Boolean checkString(String param) {
        return param != null && !param.isBlank();
    }

    public InvoiceResponse getInvoices(Long csId, String csIdPub, String msisdn, String cin, Long billingAccountId,
                                       String billingAccountCode, String regNo, String prgCodeInclude,
                                       String prgCodeExclude, String startDate, String endDate, String refFacture) {

        CustomerDetails customerDetails = null;
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        InvoicesBean invoicesBean = new InvoicesBean();

        CustomerReference custRef = new CustomerReference();

        if(startDate != null && !startDate.isBlank())
            invoicesBean.setStartDate(startDate);

        if(startDate != null && !startDate.isBlank())
            invoicesBean.setEndDate(endDate);

        if (csId != null || csIdPub != null || msisdn != null) {
            custRef.setCsId(csId != null ? csId : this.restExecutorClient.getCsIdFromMsisdn(msisdn));
            custRef.setCsIdPub(csIdPub);
            invoicesBean.setCustRef(custRef);
        }

        if (csId == null && csIdPub == null && msisdn == null) {
            customerDetails = this.restExecutorClient.getCustomerDetails(null, null, cin, regNo);
            logger.info(" +++++ details " + customerDetails);

            if (customerDetails != null && customerDetails.getCustomerId() != null) {
                custRef.setCsId(customerDetails.getCustomerId());
            } else if (customerDetails != null && customerDetails.getCustomerIds() != null && !customerDetails.getCustomerIds().isEmpty()) {

                custRef.setCsIds(customerDetails.getCustomerIds());
                logger.info(" ------------- "+custRef.getCsIds());
            }
            invoicesBean.setCustRef(custRef);
        }

        if (billingAccountId != null || billingAccountCode != null) {
            BillingAccount baRef = new BillingAccount();
            baRef.setBillingAccountCode(billingAccountCode);
            baRef.setBillingAccountId(billingAccountId);
            invoicesBean.setBaRef(baRef);
        }

        if (refFacture != null && !refFacture.isEmpty()) {
            invoicesBean.setRefFacture(refFacture);
        }


        if(prgCodeInclude != null && prgCodeInclude.equals(("1"))) {
            if (!this.processPrgCode(this.settingsProperties.getPrgcode().getInclude(),
                    csId, csIdPub, custRef, customerDetails, cin, regNo)) {
                invoicesBean.getCustRef().setCsId(0L);
            }
        }

        if(prgCodeExclude != null && prgCodeExclude.equals(("1"))) {
            if (this.processPrgCode(this.settingsProperties.getPrgcode().getExclude(),
                    csId, csIdPub, custRef, customerDetails, cin, regNo)) {
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

        logger.error("processing prgCode");
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

        if (this.settingsProperties.getDocumentSearchCount() != null) {
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
        
        Map<Long, List<DocumentsListpartResponse>> groupByBillAccId = documentsList
                .stream()
                .collect(Collectors.groupingBy((documentsListpartResponse) -> documentsListpartResponse
                        .getBillingAccountId() != null ? documentsListpartResponse.getBillingAccountId() : 0L));


        CustomerReference customerReference = new CustomerReference();

        InvoiceArrayOfInvoicesByBillingAccount arrayOfInvoicesByBillingAccount = new InvoiceArrayOfInvoicesByBillingAccount();
        List<InvoiceInvoicesByBillingAccount> invoicesByBillingAccountList = new ArrayList<>();

        groupByBillAccId.forEach((billAccId, documentList) -> {
            InvoiceArrayOfInvoiceDetails arrayOfInvoiceDetails = new InvoiceArrayOfInvoiceDetails();
            InvoiceInvoicesByBillingAccount invoicesByBillingAccount = new InvoiceInvoicesByBillingAccount();
            InvoiceBillingAccountReference billingAccountReference = new InvoiceBillingAccountReference();
            List<InvoiceInvoiceDetails> invoiceDetailsList = new ArrayList<>();
            documentList.forEach(document -> {
                customerReference.setCsId(document.getCsId());
                customerReference.setCsIdPub(document.getCsIdPub());
                billingAccountReference.setBillingAccountCode(document.getBillingAccountCode());
                billingAccountReference.setBillingAccountId(document.getBillingAccountId());
                InvoiceInvoiceDetails invoiceDetails = new InvoiceInvoiceDetails();
                OrderhdrAllDto orderHdrDto= this.orderhdrAllMapper.toDto(this.orderhdrAllRepository
                        .findByDocumentIdAndStatus(document.getDocumentId(),"IN"));
                invoiceDetails.setInvoiceType(orderHdrDto.getCostCenterId() == 1 ? Constants.INVOICE_MOBILE : Constants.INVOICE_DATA);
                invoiceDetails.setStatus(this.getInvoiceStatus(document));
                invoiceDetails.setAmountToPay(BigDecimal.valueOf(document.getOpenAmountDoc().getAmount()));
                invoiceDetails.setSentDate(document.getRefDate().toString());
                invoiceDetails.setExpectedPaymentDate(document.getDueDate() != null ? document.getDueDate().toString() : "");
                invoiceDetails.setReferenceNumber(document.getDocumentCode());
                invoiceDetails.setDocumentId(document.getDocumentId());
                invoiceDetails.setOrderNumber(BigDecimal.valueOf(document.getDocumentId()));
                invoiceDetails.setBilledAmount(BigDecimal.valueOf(document.getDocumentAmountDoc().getAmount()));
                invoiceDetailsList.add(invoiceDetails);
            });
            arrayOfInvoiceDetails.setItem(invoiceDetailsList);
            invoicesByBillingAccount.setInvoices(arrayOfInvoiceDetails);
            invoicesByBillingAccount.setBaRef(billingAccountReference);
            invoicesByBillingAccountList.add(invoicesByBillingAccount);
            arrayOfInvoicesByBillingAccount.setItem(invoicesByBillingAccountList);
        });
        invoiceResponse.setCustomerRef(customerReference);
        invoiceResponse.setInvoicesByBa(arrayOfInvoicesByBillingAccount);
        invoiceResponse.setIsSuccessful(true);
        invoiceResponse.setComment("Success");
        return invoiceResponse;
    }


    private String getInvoiceStatus(DocumentsListpartResponse document) {
        String status = document.getOpenAmountDoc().getAmount() > 0 ? Constants.NOT_PAID : Constants.PAID;

        if (!status.equalsIgnoreCase(Constants.PAID)) {
            if (this.finTrxInterfaceHistRepository.existsByDocumentIdAndCsIdAndUseCaseCode(document.getDocumentId(), document.getCsId(), UseCasesIdPub.PAYMENT.getValue()) ||
            this.finTrxInterfaceRepository.existsByDocumentIdAndCsIdAndUseCaseCode(document.getDocumentId(), document.getCsId(), UseCasesIdPub.PAYMENT.getValue())) {
                return Constants.PAYMENT_IN_PROGRESS;
            }
        }
        return status;
    }

}

