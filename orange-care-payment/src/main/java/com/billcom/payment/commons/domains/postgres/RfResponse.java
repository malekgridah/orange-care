package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.TrueFalseConverter;

@Getter
@Setter
@Entity
@Table(name = "payment_api_rf_response", schema = "alcatel")
public class RfResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_api_rf_response_gen")
    @SequenceGenerator(name = "payment_api_rf_response_gen", sequenceName = "alcatel.payment_api_rf_response_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "comment_operation")
    private String commentOperation;

    @Size(max = 255)
    @Column(name = "errorcode")
    private String errorcode;

    @Column(name = "issuccessful")
    @Convert(converter = TrueFalseConverter.class)
    private Boolean issuccessful;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "failedoperation_id")
    private FailedOperation failedOperation;

}
