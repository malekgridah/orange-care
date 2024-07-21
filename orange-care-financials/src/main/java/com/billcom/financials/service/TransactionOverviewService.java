package com.billcom.financials.service;

import com.billcom.financials.clients.wsi.FinancialTransactionDetailReadClient;
import com.billcom.financials.clients.wsi.FinancialTransactionReadClient;
import com.billcom.financials.commons.Money;
import com.billcom.financials.commons.beans.overview.TransactionOverview;
import com.billcom.financials.commons.beans.overview.TransactionOverviewDetails;
import com.billcom.financials.commons.beans.overview.TransactionOverviewRequest;
import com.billcom.financials.commons.beans.overview.TransactionOverviewResponse;
import com.billcom.financials.utils.FMSConvertor;
import com.ericsson.financialtransactionread.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionOverviewService {
    private static final Logger logger = LogManager.getLogger(TransactionOverviewService.class);

    private final FinancialTransactionReadClient transactionReadClient;
    private final FinancialTransactionDetailReadClient transactionDetailReadClient;

    @Autowired
    public TransactionOverviewService(FinancialTransactionReadClient transactionReadClient,
                                      FinancialTransactionDetailReadClient transactionDetailReadClient) {
        this.transactionReadClient = transactionReadClient;
        this.transactionDetailReadClient = transactionDetailReadClient;
    }

    public TransactionOverviewResponse overview(TransactionOverviewRequest request) {
        TransactionOverviewResponse response = new TransactionOverviewResponse();
        FinancialTransactionReadRequest readRequest = new FinancialTransactionReadRequest();
        InputAttributes inputAttributes = new InputAttributes();

        inputAttributes.setTransactionId(request.getTransactionId());
        readRequest.setInputAttributes(inputAttributes);

        FinancialTransactionReadResponse transactionReadResponse = this.transactionReadClient.execute(readRequest);

        if (transactionReadResponse != null) {
            logger.info("Search successful with {} transactions found", transactionReadResponse.getTransactionId());
            response.setSuccessful(true);
            response.setTransactionOverview(this.getTransaction(transactionReadResponse));
        }else {
            logger.warn("Search returned no transactions");
        }
        return response;
    }

    private TransactionOverview getTransaction(FinancialTransactionReadResponse transactionReadResponse) {
        TransactionOverview transactionOverview = new TransactionOverview();
        transactionOverview.setTransactionId(transactionReadResponse.getTransactionId());
        transactionOverview.setTransactionReference(transactionReadResponse.getTransactionRefnum());
        transactionOverview.setTransactionCodePub(transactionReadResponse.getTransCodePub());

        transactionOverview.setCsId(transactionReadResponse.getCsId());
        transactionOverview.setCsIdPub(transactionReadResponse.getCsIdPub());
        transactionOverview.setBillingAccountId(transactionReadResponse.getBillingAccountId());
        transactionOverview.setBillingAccountCode(transactionReadResponse.getBillingAccountCode());

        transactionOverview.setUser(transactionReadResponse.getUser());
        transactionOverview.setPayMethodIdPub(transactionReadResponse.getPayMethodIdPub());
        transactionOverview.setUseCaseCode(transactionReadResponse.getUseCaseCode());
        transactionOverview.setReversalFlag(transactionReadResponse.isReversalFlag());
        transactionOverview.setReversed(transactionReadResponse.isReversed());
        transactionOverview.setPaymentChannel(transactionReadResponse.getPayChannelIdPub());
        transactionOverview.setRevOrigTransactionId(transactionReadResponse.getRevOrigTransactionId());
        transactionOverview.setGlAccount(transactionReadResponse.getGlAccount());
        transactionOverview.setEntryDate(transactionReadResponse.getEntryDate()
                .toGregorianCalendar()
                .toZonedDateTime()
                .toLocalDateTime());
        transactionOverview.setEffectiveDate(transactionReadResponse.getEffectiveDate()
                .toGregorianCalendar()
                .toZonedDateTime()
                .toLocalDateTime());
        transactionOverview.setReferenceDate(transactionReadResponse.getRefDate()
                .toGregorianCalendar()
                .toZonedDateTime()
                .toLocalDateTime());

        transactionOverview.setAmountCashPay(FMSConvertor.toMoney(transactionReadResponse.getAmountCashPay().getAmount(), transactionReadResponse.getAmountCashPay().getCurrency()));
        transactionOverview.setAmountCurrentPay(FMSConvertor.toMoney(transactionReadResponse.getAmountCurrentPay().getAmount(), transactionReadResponse.getAmountCurrentPay().getCurrency()));
        transactionOverview.setTransactionDetails(this.overviewDetails(transactionReadResponse.getTransItems()));
        return transactionOverview;
    }

    private List<TransactionOverviewDetails> overviewDetails(TransItemsResponse transItems) {
        List<TransactionOverviewDetails> detailsList = new ArrayList<>();
        if (transItems != null && !transItems.getItem().isEmpty()) {
            transItems.getItem().forEach(item -> {
                TransactionOverviewDetails details = new TransactionOverviewDetails();
                details.setDocumentId(item.getDocumentId());
                details.setDocumentCode(item.getDocumentCode());
                details.setGlAccountRevenue(item.getGlAccountRevenue());
                details.setAmountPay(FMSConvertor.toMoney(item.getAmountPay().getAmount(), item.getAmountPay().getCurrency()));
                details.setAmountGrossPay(FMSConvertor.toMoney(item.getAmountGrossPay().getAmount(), item.getAmountGrossPay().getCurrency()));
                details.setTaxAmountPay(this.getTax(item.getTransItemTaxItems()));
                detailsList.add(details);
            });
        }
        return detailsList;
    }

    private Money getTax(TransItemTaxItemsResponse taxItem) {
        if (taxItem != null) {
            return taxItem.getItem()
                    .stream()
                    .map(tax -> FMSConvertor.toMoney(tax.getTaxAmountPay().getAmount(), tax.getTaxAmountPay().getCurrency()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
