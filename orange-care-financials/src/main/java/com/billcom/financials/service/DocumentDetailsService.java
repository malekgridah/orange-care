package com.billcom.financials.service;

import com.billcom.financials.clients.wsi.FinancialDocumentDetailReadClient;
import com.billcom.financials.commons.beans.document.DocumentDetails;
import com.billcom.financials.commons.beans.document.DocumentDetailsRequest;
import com.billcom.financials.commons.beans.document.DocumentDetailsResponse;
import com.billcom.financials.commons.beans.document.DocumentTransaction;
import com.billcom.financials.utils.FMSConvertor;
import com.ericsson.financialdocumentdetailread.FinancialDocumentDetailReadRequest;
import com.ericsson.financialdocumentdetailread.FinancialDocumentDetailReadResponse;
import com.ericsson.financialdocumentdetailread.InputAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentDetailsService {
    private static final Logger logger = LogManager.getLogger(DocumentDetailsService.class);

    private final FinancialDocumentDetailReadClient documentDetailReadClient;

    @Autowired
    public DocumentDetailsService(FinancialDocumentDetailReadClient documentDetailReadClient) {
        this.documentDetailReadClient = documentDetailReadClient;
    }

    public DocumentDetailsResponse documentDetails(DocumentDetailsRequest request) {
        DocumentDetailsResponse response = new DocumentDetailsResponse();
        FinancialDocumentDetailReadRequest detailRequest = new FinancialDocumentDetailReadRequest();
        InputAttributes inputAttributes = new InputAttributes();

        inputAttributes.setDocumentId(request.getDocumentId());
        detailRequest.setInputAttributes(inputAttributes);

        FinancialDocumentDetailReadResponse detailResponse = this.documentDetailReadClient.execute(detailRequest);

        if (detailResponse != null) {
            response.setSuccessful(true);
            response.setDocumentDetails(this.prepareDocumentDetails(detailResponse));
        }
        return response;
    }

    private DocumentDetails prepareDocumentDetails(FinancialDocumentDetailReadResponse detailResponse) {
        DocumentDetails response = new DocumentDetails();
        List<DocumentTransaction> transactions = new ArrayList<>();
        response.setDocumentId(detailResponse.getDocumentId());
        response.setDocumentCode(detailResponse.getDocumentCode());
        if (detailResponse.getItems() != null && !detailResponse.getItems().getItem().isEmpty()) {
            detailResponse.getItems().getItem().forEach(item -> {
                DocumentTransaction transaction = new DocumentTransaction();
                transaction.setTransactionId(item.getTransactionId());
                transaction.setTransactionReference(item.getTransactionRefnum());
                transaction.setTransCodePub(item.getTransCodePub());
                transaction.setUseCaseCode(item.getUseCaseCode());

                transaction.setAmountCashPay(FMSConvertor.toMoney(item.getAmountCashPay().getAmount(), item.getAmountCashPay().getCurrency()));
                transaction.setAmountCurrentPay(FMSConvertor.toMoney(item.getAmountCurrentPay().getAmount(), item.getAmountCurrentPay().getCurrency()));
                transaction.setAmountCurrentDoc(FMSConvertor.toMoney(item.getAmountCurrentDoc().getAmount(), item.getAmountCurrentDoc().getCurrency()));

                transaction.setItemCreditDebitType(item.getItemCreditDebitType());

                transaction.setEntryDate(item.getEntryDate()
                        .toGregorianCalendar()
                        .toZonedDateTime()
                        .toLocalDate());
                transaction.setRefDate(item.getEntryDate()
                        .toGregorianCalendar()
                        .toZonedDateTime()
                        .toLocalDate());
                transactions.add(transaction);
            });
            response.setTransactions(transactions);
        }
        return response;
    }
}
