package com.billcom.bscs.beans;

import lombok.Data;

@Data
public class Rateplan {
    private Long rpCode;
    private String rpCodePub;
    private String rpDes;
    private Boolean rpOcc;
    private String scope;
}
