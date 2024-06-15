package com.billcom.payment.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public interface I18nErrorMessages {

	String RESOURCE_BUNDLE_NAME = "messagesBundle.ErrorMessagesBundle";

	String CLIENT_LOCALE = "clientLocale_";
	String MANDATORY_Parameter = "MandatoryParameter";
	String MANDATORY_OBJECT = "MandatoryObject";
	String ONE_ATTRIBUTE_MANDATORY = "AtLeastOneAttributeProvided";
	String ONE_OBJECT_MANDATORY = "AtLeastOneObjectMandatory";

	String INVALID_PARAMETER = "InvalidParameter";
	String TECHNICAL_PROBLEM = "TechnicalIssue";
	String BSCS_PROBLEM = "BSCSTechnicalProblem";


	String DOCUMENT_ALREADY_PAID = "DocumentAlreadyPaid";
	String TRANSACTION_ALREADY_REVERSED = "TransactionAlreadyReversed";
	String DOCUMENT_ALREADY_REVERSED = "DocumentAlreadyReversed";

	String AUTHENTICATION_MISSING_CREDENTIALS = "MissingAuthentication";
	String AUTHENTICATION_USER_DISABLED = "InactiveAccount";
	String AUTHENTICATION_WRONG_PASSWORD = "InvalidPassword";
	String AUTHENTICATION_USER_NOT_FOUND = "UserNotFound";
	String MISSING_AUTHENTICATION_HEADER = "AuthenticationHeaderMissing";
	String MISSING_CONFIG = "";
	String INVALID_AMOUNT = "";
	String RECHARGE_IN_DUPLICATE_TRS_ID = "RechargeAlreadyExecuted";
	String MAX_DAILY_RECHARGE_REACHED = "MaxDailyRechargeReached";
	String INVALID_RECHARGE_AMOUNT = "InvalidRechargeAmount";




	static ResourceBundle getMessages(Locale loc) {
		return ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, loc);
	}
}
