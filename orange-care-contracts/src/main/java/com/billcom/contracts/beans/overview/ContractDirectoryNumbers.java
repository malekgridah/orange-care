package com.billcom.contracts.beans.overview;

import lombok.Data;

@Data
public class ContractDirectoryNumbers {
    private Long snCode;
    private String snCodePub;
    private String snCodeDes;

    private Long spCode;
    private String spCodePub;
    private String spCodeDes;

    private Long profileId;
    private String dirNum;
    private String dnStatus;

    private Boolean mainDirNum;
    private Boolean dirNumOnBill;
}
