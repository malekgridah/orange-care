package com.billcom.payment.commons.exceptions;

import com.billcom.payment.utils.I18nErrorMessages;
import jakarta.xml.ws.WebFault;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@WebFault
public class InvalidInputValueException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -67544848636138787L;

    private final Object[] errorArgs;

    public InvalidInputValueException(Object... errorArgs) {
        super();
        this.errorArgs = errorArgs;
    }

    @Override
    public String getMessage() {
        Locale locale = Locale.getDefault();
        ResourceBundle messages = ResourceBundle.getBundle(I18nErrorMessages.RESOURCE_BUNDLE_NAME, locale);
        String errorComment = MessageFormat.format(messages.getString(I18nErrorMessages.INVALID_PARAMETER), errorArgs);
        return I18nErrorMessages.INVALID_PARAMETER + " : " + errorComment;
    }
}
