package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.SmsConfig;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link SmsConfig}
 */

@Value
@Builder
public class SmsConfigDto implements Serializable {
    BigDecimal id;
    @NotNull
    @Size(max = 20)
    String canal;
    @NotNull
    @Size(max = 10)
    String prgCode;
    @NotNull
    BigDecimal enable;
    @Size(max = 255)
    String text;
    @Size(max = 255)
    String textCancelPay;
}
