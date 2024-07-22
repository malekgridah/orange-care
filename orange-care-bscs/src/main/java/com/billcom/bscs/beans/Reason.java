package com.billcom.bscs.beans;

import lombok.Data;

@Data
public class Reason {
    protected Long rsCode;
    protected String rsDes;
    protected String rsState;
    protected String scope;
}
