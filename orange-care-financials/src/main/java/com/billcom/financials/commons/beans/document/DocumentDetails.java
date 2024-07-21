package com.billcom.financials.commons.beans.document;

import lombok.Data;

import java.util.List;

@Data
public class DocumentDetails {
    private Long documentId;
    private String documentCode;
    private List<DocumentTransaction> transactions;
}
