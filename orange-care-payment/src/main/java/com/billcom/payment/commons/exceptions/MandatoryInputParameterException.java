package com.billcom.payment.commons.exceptions;

import com.billcom.payment.utils.I18nErrorMessages;
import jakarta.xml.ws.WebFault;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@WebFault
public class MandatoryInputParameterException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -67005208536138787L;

    public MandatoryInputParameterException(String message){
        super(message);
    }

    public String getMessage() {
        super.fillInStackTrace();
        Locale locale = Locale.getDefault();
        ResourceBundle messages = ResourceBundle.getBundle(I18nErrorMessages.RESOURCE_BUNDLE_NAME, locale);
        String errorComment = MessageFormat.format(messages.getString(I18nErrorMessages.MANDATORY_Parameter),super.getMessage());
        return I18nErrorMessages.MANDATORY_Parameter + " : " + errorComment;
    }

}



