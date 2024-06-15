package com.billcom.bscs.commons.beans.bscs;

import lombok.Data;

@Data
public class Currency {
    private Long currencyId;
    private String currencyIdPub;
    private String fcCode;
    private String fcDesc;
}
