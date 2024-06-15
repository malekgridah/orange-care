package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.Log;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link Log}
 */
@Value
@Builder
public class LogDto implements Serializable {

    @Size(max = 255)
    String description;
    @Size(max = 255)
    String errorCode;
    Date logDate;
    @Size(max = 255)
    String montant;
    @Size(max = 255)
    String operation;
    @Size(max = 255)
    String platform;
    @Size(max = 255)
    String rechargeId;
    @Size(max = 255)
    String refFacture;
    @Size(max = 255)
    String status;
    Long trsId;
    @Size(max = 255)
    String userLog;
}
