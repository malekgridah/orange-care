package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.OperationResponse;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link OperationResponse}
 */
@Value
@Builder
public class OperationResponseDto implements Serializable {
    Long id;
    @Size(max = 255)
    String comment;
    String errorCode;
    String isSuccessful;
    LocalDateTime entryDate;
}
