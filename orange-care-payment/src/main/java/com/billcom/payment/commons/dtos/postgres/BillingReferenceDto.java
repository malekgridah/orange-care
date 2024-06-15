package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.BillingReference;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link BillingReference}
 */
@Value
@Builder
public class BillingReferenceDto implements Serializable {
    Long id;
    Long billingAccountId;
    String billingAccountCode;

}
