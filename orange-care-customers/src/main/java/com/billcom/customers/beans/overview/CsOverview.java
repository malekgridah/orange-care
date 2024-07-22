package com.billcom.customers.beans.overview;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CsOverview {
    private String csIdPub;
    private String csCode;
    private String csStatus;
    private LocalDateTime csStatusDate;
    private String csEmail;
    private String csPassword;
    private String csLanguage;
    private String csAddress;
    private String csBillcycle;
}
