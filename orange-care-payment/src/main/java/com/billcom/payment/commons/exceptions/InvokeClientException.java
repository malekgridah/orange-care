package com.billcom.payment.commons.exceptions;

import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.WebFault;

import java.io.Serial;

@WebFault
public class InvokeClientException extends RuntimeException{

    private SOAPFault soapFault;

    @Serial
    private static final long serialVersionUID = -67895540036138787L;

    public InvokeClientException(Throwable cause) {
        super(cause);

    }

    public InvokeClientException(SOAPFault soapFault, Throwable throwable){
        super(throwable);
        this.soapFault = soapFault;
    }

    public String getMessage() {
        if(this.soapFault != null)
            return this.soapFault.getFaultCode().split(":")[1] + " : " + this.soapFault.getFaultString();
        return super.getMessage();
    }

}
