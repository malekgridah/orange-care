package com.billcom.payment.commons.dtos.bscs;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.billcom.payment.commons.domains.bscs.OrderhdrAll}
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class OrderhdrAllDto implements Serializable {
    private final Long documentId;
    @Size(max = 30)
    private final String documentCode;
    private final LocalDate refDate;
    private final LocalDate dueDate;
    private final Long costCenterId;
}
