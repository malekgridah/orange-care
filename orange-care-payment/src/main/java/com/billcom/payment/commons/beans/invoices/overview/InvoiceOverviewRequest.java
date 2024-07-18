package com.billcom.payment.commons.beans.invoices.overview;

import lombok.Data;

@Data
public class InvoiceOverviewRequest {
    private Long documentId;
    private String documentCode;
}
