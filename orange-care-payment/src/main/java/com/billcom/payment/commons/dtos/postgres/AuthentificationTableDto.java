package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.AuthentificationTable;

import java.io.Serializable;

/**
 * DTO for {@link AuthentificationTable}
 */

@Value
@Builder
public class AuthentificationTableDto implements Serializable {
    AuthentificationTableKeyDto authentificationKey;
    String login;
    String password;
}
