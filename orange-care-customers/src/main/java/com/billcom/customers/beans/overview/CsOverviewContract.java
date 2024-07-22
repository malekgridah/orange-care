package com.billcom.customers.beans.overview;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CsOverviewContract {
    private Long coId;
    private String dirNum;
    private String coIdPub;
    private Long rpCode;
    private String rateplan;
    private Integer coStatus;
    private LocalDateTime coActDate;
}
