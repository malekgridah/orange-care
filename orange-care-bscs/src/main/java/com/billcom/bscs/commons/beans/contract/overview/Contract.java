package com.billcom.bscs.commons.beans.contract.overview;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Contract {
    private Long coId;
    private String coCode;
    private Long csId;
    private String csIdPub;
    private Long scCode;
    private String scCodePub;
    private Long subMarket;
    private String subMarketIdPub;

    private Integer coStatus;
    private Long coLastReason;
    private String coLastReasonShdes;
    private Long reason;
    private String reasonShdes;

    private LocalDateTime coPendingDate;
    private LocalDateTime coLastStatusChangeDate;
    private LocalDateTime coModDate;
    private LocalDateTime coEntDate;
    private LocalDateTime coActivatedDate;
    private LocalDate coSignedDate;

    private ContractResources resources;
    private ContractDevices devices;

    private ContractServiceNode[] contractServiceNode;
}
