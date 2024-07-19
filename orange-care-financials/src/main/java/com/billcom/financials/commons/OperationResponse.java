package com.billcom.financials.commons;

import lombok.Data;

@Data
public abstract class OperationResponse {
    private String error;
    private String comment;
    private Boolean successful;
}
