package com.billcom.contracts.beans.create;

import lombok.Data;

@Data
public class ContractCreateRequest {
    private String coStatus;
    private Long reason;
    private Long csId;
    private String csIdIPub;
}
