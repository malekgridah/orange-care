package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.GetInvoice;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link GetInvoice}
 */
@Value
@Builder
public class GetInvoiceDto implements Serializable {
    Long id;
    Instant endDate;
    Instant startDate;
    Long trsId;

}
