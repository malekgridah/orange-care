package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.RfResponse;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link RfResponse}
 */
@Value
@Builder
public class RfResponseDto implements Serializable {
    Long id;
    @Size(max = 255)
    String commentOperation;
    @Size(max = 255)
    String errorCode;
    String isSuccessful;
    @Size(max = 255)
    String errorcode;
    String issuccessful;
    FailedOperationDto failedoperation;
}
