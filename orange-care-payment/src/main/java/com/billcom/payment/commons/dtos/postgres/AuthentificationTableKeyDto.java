package com.billcom.payment.commons.dtos.postgres;

import java.io.Serializable;

/**
 * DTO for {@link com.billcom.payment.commons.domains.postgres.AuthentificationTableKey}
 */

@Builder
@Value
public class AuthentificationTableKeyDto implements Serializable {
    String canal;
    String operationType;
}
