package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.RechargeInParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link RechargeInParam}
 */
@Value
@Builder
public class RechargeInParamDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 64)
    String rechargeId;
    @NotNull
    @Size(max = 64)
    String refillProfilId;
    @NotNull
    @Size(max = 64)
    String minTransactionAmount;
    @NotNull
    @Size(max = 64)
    String maxTransactionAmount;
    String canalName;
    String canal;
}
