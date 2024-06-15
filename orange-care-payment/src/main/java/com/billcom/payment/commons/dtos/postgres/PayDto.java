package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.Pay;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Pay}
 */
@Value
@Builder
public final class PayDto implements Serializable {
    @Size(max = 255)
    private final String bankName;

    private final Long btCaxact;

    private final Long btOhxact;

    private final Long csId;

    @Size(max = 255)
    private final String csIdPub;

    @Size(max = 255)
    private final String cspAccNo;

    @Size(max = 255)
    private final String cspAccOwner;

    @Size(max = 255)
    private final String cspBankCode;

    @Size(max = 255)
    private final String glaCash;

    @Size(max = 255)
    private final String glaDis;

    private final Long paymentCurrencyId;

    @Size(max = 255)
    private final String paymentMode;

    @Size(max = 255)
    private final String rtCachknum;

    @Size(max = 255)
    private final String rtCarem;

    @Size(max = 255)
    private final String rtCauserName;

    private final Long rtExchangeRateCurrency;

    @Size(max = 255)
    private final String rtFcCodePay;

    private final Boolean synchronousMode;

    @Size(max = 255)
    private final String transxCode;

    private final Long trsId;

    @Size(max = 255)
    private final String validThroughDate;

    private final LocalDateTime entryDate;

    @Size(max = 20)
    private final String canal;

    @Size(max = 50)
    private final String operationType;

    @Size(max = 50)
    private final String operationState;

}
