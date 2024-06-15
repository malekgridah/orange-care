package com.billcom.payment.commons.exceptions;

import com.billcom.payment.utils.I18nErrorMessages;
import jakarta.xml.ws.WebFault;

import java.io.Serial;

@WebFault
public class BadRequestException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -67544848636138787L;

    public BadRequestException(String message){
        super(message);
    }

    public String getMessage() {
        return I18nErrorMessages.MANDATORY_OBJECT + super.getMessage() + " Please see the server log to find more detail regarding exact cause of the failure.";
    }
}
