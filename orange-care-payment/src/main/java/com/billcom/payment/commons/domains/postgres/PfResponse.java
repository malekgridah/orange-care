package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.TrueFalseConverter;

@Getter
@Setter
@Entity
@Table(name = "payment_api_pf_response", schema = "alcatel")
public class PfResponse {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "comment_operation")
    private String commentOperation;

    @Size(max = 255)
    @Column(name = "errorcode")
    private String errorCode;

    @Column(name = "issuccessful")
    @Convert(converter = TrueFalseConverter.class)
    private Boolean isSuccessful;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "failedoperation_id")
    private FailedOperation failedOperation;

}
