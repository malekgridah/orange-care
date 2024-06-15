package com.billcom.payment.commons.exceptions;

import jakarta.xml.ws.WebFault;

import java.io.Serial;

@WebFault
public class DataNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -67544848636138787L;

    public DataNotFoundException(String message){
        super(message);
    }



    public String getMessage() {
        return super.getMessage();
    }
}
