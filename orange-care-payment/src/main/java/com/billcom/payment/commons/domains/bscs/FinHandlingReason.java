package com.billcom.payment.commons.domains.bscs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "FIN_HANDLING_REASON")
public class FinHandlingReason {
    @Id
    @Column(name = "HANDLING_REASON_ID", nullable = false)
    private Long id;

    @Size(max = 15)
    @NotNull
    @Column(name = "SHDES", nullable = false, length = 15)
    private String handlingReasonIdPub;

    @Size(max = 100)
    @NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String HandlingDesc;

}
