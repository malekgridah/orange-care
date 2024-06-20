package com.billcom.payment.commons.dtos.postgres;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.billcom.payment.commons.domains.postgres.AuthentificationTableKey}
 */

@Value
@Builder
public class AuthentificationTableKeyDto implements Serializable {
    String canal;
    String operationType;
}
