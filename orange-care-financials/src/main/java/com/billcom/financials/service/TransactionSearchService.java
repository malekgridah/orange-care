package com.billcom.financials.service;

import com.billcom.financials.clients.wsi.FinancialTransactionSearchClient;
import com.billcom.financials.commons.beans.search.TransactionSearch;
import com.billcom.financials.commons.beans.search.TransactionSearchRequest;
import com.billcom.financials.commons.beans.search.TransactionSearchResponse;
import com.billcom.financials.utils.FMSConvertor;
import com.ericsson.financialtransactionsearch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionSearchService {
    private static final Logger logger = LogManager.getLogger(TransactionSearchService.class);

    private final FinancialTransactionSearchClient searchClient;

    @Autowired
    public TransactionSearchService(FinancialTransactionSearchClient searchClient) {
        this.searchClient = searchClient;
    }

    public TransactionSearchResponse search(TransactionSearchRequest request) {
        logger.info("Initiating transaction search with request...");
        TransactionSearchResponse response = new TransactionSearchResponse();
        FinancialTransactionSearchRequest transactionSearchRequest = new FinancialTransactionSearchRequest();
        InputAttributes inputAttributes = new InputAttributes();

        if (request.getTransactionId() != null) inputAttributes.setTransactionId(request.getTransactionId());
        if (request.getTransactionReference() != null) inputAttributes.setTransactionRefnum(request.getTransactionReference());
        if (request.getTransactionStatus() != null) inputAttributes.setTransactionStatus(request.getTransactionStatus());

        if (request.getCsId() != null) inputAttributes.setCsId(request.getCsId());
        if (request.getCsIdPub() != null) inputAttributes.setCsIdPub(request.getCsIdPub());

        if (request.getBillingAccountId() != null) inputAttributes.setBillingAccountId(request.getBillingAccountId());
        if (request.getBillingAccountCode() != null) inputAttributes.setBillingAccountCode(request.getBillingAccountCode());

        if (request.getSearchCount() != null) inputAttributes.setResultLimit(BigInteger.valueOf(request.getSearchCount()));
        else inputAttributes.setResultLimit(BigInteger.valueOf(20L));

        if (request.getOrderBy() != null) inputAttributes.setOrderBy(request.getOrderBy());
        if (request.getTransactionCodes() != null && !request.getTransactionCodes().isEmpty()) {
            TransCodesRequest transCodesRequest = new TransCodesRequest();
            transCodesRequest.getTransCodePub().addAll(request.getTransactionCodes());
            inputAttributes.setTransCodes(transCodesRequest);
        }

        if (request.getDateFrom() != null)
            inputAttributes.setEntryDateUntil(FMSConvertor.toXMLGregorianCalendar(request.getDateFrom()));

        if (request.getDateUntil() != null)
            inputAttributes.setEntryDateUntil(FMSConvertor.toXMLGregorianCalendar(request.getDateUntil()));

        transactionSearchRequest.setInputAttributes(inputAttributes);

        FinancialTransactionSearchResponse searchResponse = this.searchClient.execute(transactionSearchRequest);

        if (searchResponse != null && searchResponse.getTransactions() != null) {
            logger.info("Search successful with {} transactions found", searchResponse.getTransactions().getItem().size());
            response.setSuccessful(true);
            response.setTransactions(this.getTransactions(searchResponse.getTransactions()));
        }else {
            logger.warn("Search returned no transactions");
        }
        return response;
    }

    private List<TransactionSearch> getTransactions(TransactionsResponse transactions) {
        logger.debug("Processing transactions...");
        List<TransactionSearch> transactionSearchList = new ArrayList<>();
        if (transactions != null && !transactions.getItem().isEmpty()) {
            transactions.getItem().forEach(item -> {
                TransactionSearch transactionSearch = new TransactionSearch();

                transactionSearch.setTransactionId(item.getTransactionId());
                transactionSearch.setTransactionReference(item.getTransactionRefnum());
                transactionSearch.setCsId(item.getCsId());
                transactionSearch.setCsIdPub(item.getCsIdPub());
                transactionSearch.setGlAccount(item.getGlAccount());
                transactionSearch.setTransactionCodePub(item.getTransCodePub());
                transactionSearch.setTransactionType(item.getTransactionType());
                transactionSearch.setPaymentMethodId(item.getPaymentMethodId());
                transactionSearch.setCashPayAmount(FMSConvertor.toMoney(item.getAmountCashPay().getAmount(), item.getAmountCashPay().getCurrency()));
                transactionSearch.setCurrentAmount(FMSConvertor.toMoney(item.getAmountCurrent().getAmount(), item.getAmountCurrent().getCurrency()));
                transactionSearch.setReferenceDate(item.getRefDate()
                        .toGregorianCalendar()
                        .toZonedDateTime()
                        .toLocalDateTime());
                transactionSearch.setEntryDate(item.getEntryDate()
                        .toGregorianCalendar()
                        .toZonedDateTime()
                        .toLocalDateTime());
                logger.debug("Processed transaction Id: {}", transactionSearch.getTransactionId());
                transactionSearchList.add(transactionSearch);
            });
        }
        return transactionSearchList;
    }
}
