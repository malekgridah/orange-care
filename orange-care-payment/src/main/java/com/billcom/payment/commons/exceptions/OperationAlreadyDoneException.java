package com.billcom.payment.commons.exceptions;

import com.billcom.payment.utils.I18nErrorMessages;
import jakarta.xml.ws.WebFault;
import lombok.Getter;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@WebFault
public class OperationAlreadyDoneException extends RuntimeException {

    @Getter
    private final String errorCode;
    private final String parameter;
    @Serial
    private static final long serialVersionUID = -67005208536138787L;

    public OperationAlreadyDoneException(String errorCode, String parameter) {
        this.errorCode = errorCode;
        this.parameter = parameter;
    }

    public String getMessage() {
        Locale locale = Locale.getDefault();
        ResourceBundle messages = ResourceBundle.getBundle(I18nErrorMessages.RESOURCE_BUNDLE_NAME, locale);
        String errorComment = MessageFormat.format(messages.getString(errorCode), parameter);
        return errorCode + " : " + errorComment;
    }

}
