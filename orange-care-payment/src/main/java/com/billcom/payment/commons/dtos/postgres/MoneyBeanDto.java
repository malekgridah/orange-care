package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.MoneyBean;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link MoneyBean}
 */
@Value
@Builder
public class MoneyBeanDto implements Serializable {
    Long id;
    Double amount;
    @Size(max = 255)
    String currency;
}
