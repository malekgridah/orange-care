package com.billcom.payment.commons.exceptions;

import com.billcom.payment.utils.I18nErrorMessages;
import jakarta.xml.ws.WebFault;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@WebFault
public class AtLeastMandatoryInputValueException extends RuntimeException {

    @Getter
    private final String errorCode;
    private final Object[] errorArgs;

    public AtLeastMandatoryInputValueException(String errorCode, Object... errorArgs) {
        super();
        this.errorCode = errorCode;
        this.errorArgs = errorArgs;
    }

    @Override
    public String getMessage() {
        Locale locale = Locale.getDefault();
        ResourceBundle messages = ResourceBundle.getBundle(I18nErrorMessages.RESOURCE_BUNDLE_NAME, locale);
        String errorComment = MessageFormat.format(messages.getString(errorCode), errorArgs);
        return errorCode + " : " + errorComment;
    }

}
