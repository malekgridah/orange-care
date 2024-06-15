package com.billcom.payment.commons.dtos.postgres;

import com.billcom.payment.commons.domains.postgres.CustomerReference;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link CustomerReference}
 */
@Value
@Builder
public class CustomerReferenceDto implements Serializable {
    Long id;
    Long csId;
    String csIdPub;

}
