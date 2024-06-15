package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payment_api_log", schema = "alcatel")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_api_log_id_gen")
    @SequenceGenerator(name = "payment_api_log_id_gen", sequenceName = "alcatel.payment_api_log_log_id_seq", allocationSize = 1)
    @Column(name = "log_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "errorcode")
    private String errorCode;

    @Column(name = "logdate")
    private Instant logDate;

    @Size(max = 255)
    @Column(name = "montant")
    private String montant;

    @Size(max = 255)
    @Column(name = "operation")
    private String operation;

    @Size(max = 255)
    @Column(name = "platform")
    private String platform;

    @Size(max = 255)
    @Column(name = "rechargeid")
    private String rechargeId;

    @Size(max = 255)
    @Column(name = "reffacture")
    private String refFacture;

    @Size(max = 255)
    @Column(name = "statuts")
    private String status;

    @Column(name = "trsid")
    private Long trsId;

    @Size(max = 255)
    @Column(name = "userlog")
    private String userLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operationresponse_response_id")
    private OperationResponse operationResponse;

}
