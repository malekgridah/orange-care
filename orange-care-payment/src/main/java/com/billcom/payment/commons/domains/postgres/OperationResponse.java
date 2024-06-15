package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.type.TrueFalseConverter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment_api_operation_response", schema = "alcatel")
public class OperationResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_api_operation_response_gen")
    @SequenceGenerator(name = "payment_api_operation_response_gen", sequenceName = "alcatel.payment_api_operation_response_response_id_seq", allocationSize = 1)
    @Column(name = "response_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "comment_operation")
    private String comment;

    @Size(max = 255)
    @Column(name = "errorcode")
    private String errorCode;

    @Column(name = "issuccessful")
    @Convert(converter = TrueFalseConverter.class)
    private Boolean isSuccessful;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

}
