package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.PfResponse;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link PfResponse}
 */
@Value
@Builder
public class PfResponseDto implements Serializable {
    Long id;
    @Size(max = 255)
    String commentOperation;
    @Size(max = 255)
    String errorCode;
    String isSuccessful;
}
