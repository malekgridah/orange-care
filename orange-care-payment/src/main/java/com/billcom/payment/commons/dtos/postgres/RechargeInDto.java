package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.RechargeIn;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link RechargeIn}
 */
@Value
@Builder
public class RechargeInDto implements Serializable {
    Long id;
    @Size(max = 255)
    String externalData1;
    @Size(max = 255)
    String externalData2;
    @Size(max = 255)
    String msisdn;
    @Size(max = 255)
    String refillProfilId;
    @Size(max = 255)
    String taxCode;
    @Size(max = 255)
    String transactionAmount;
    @Size(max = 255)
    String trsId;
    LocalDateTime entryDate;
    @Size(max = 25)
    String canal;
    OperationResponseDto operationResponse;
}
