package com.billcom.payment.service;

import com.billcom.payment.clients.soap.wsi.FinancialDocumentDetailReadClient;
import com.billcom.payment.clients.soap.wsi.FinancialDocumentReadClient;
import com.billcom.payment.commons.beans.invoices.overview.InvoiceOverviewRequest;
import com.billcom.payment.commons.beans.invoices.overview.InvoiceOverviewResponse;
import com.ericsson.financialdocumentread.FinancialDocumentReadRequest;
import com.ericsson.financialdocumentread.InputAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceOverviewService {
    private static final Logger logger = LogManager.getLogger(InvoiceOverviewService.class);

    private final FinancialDocumentDetailReadClient financialDocumentDetailReadClient;
    private final FinancialDocumentReadClient financialDocumentReadClient;

    @Autowired
    public InvoiceOverviewService(FinancialDocumentDetailReadClient financialDocumentDetailReadClient,
                                  FinancialDocumentReadClient financialDocumentReadClient) {
        this.financialDocumentDetailReadClient = financialDocumentDetailReadClient;
        this.financialDocumentReadClient = financialDocumentReadClient;
    }

    public InvoiceOverviewResponse invoiceOverView(InvoiceOverviewRequest request) {

        FinancialDocumentReadRequest readClient = new FinancialDocumentReadRequest();
        InputAttributes inputAttributes = new InputAttributes();

        inputAttributes.setDocumentId(request.getDocumentId());
        readClient.setInputAttributes(inputAttributes);

        this.financialDocumentReadClient.execute(readClient, "", "");




        return new InvoiceOverviewResponse();
    }
}
