package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.DateBean;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link DateBean}
 */
@Value
@Builder
public class DateBeanDto implements Serializable {
    Long id;
    Instant dateTime;
}
