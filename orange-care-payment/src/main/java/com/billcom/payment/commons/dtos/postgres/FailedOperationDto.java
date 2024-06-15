package com.billcom.payment.commons.dtos.postgres;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.billcom.payment.commons.domains.postgres.FailedOperation}
 */
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class FailedOperationDto implements Serializable {
    private final Long id;
    
    private final int counter;
    
    @Size(max = 255)
    private final String invoiceReference;
    
    @Size(max = 255)
    private final String status;
    
    @Size(max = 255)
    private final String type;
    
    private final PayDto pay;
    
    private final RechargeInDto rechargeIn;
    
    private final LocalDateTime entryDate;
    
    private final Set<PfResponseDto> PfResponses;
    
    private final Set<RfResponseDto> RfResponses;
}
