package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment_api_recharge_in", schema = "alcatel")
public class RechargeIn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_api_recharge_in_gen")
    @SequenceGenerator(name = "payment_api_recharge_in_gen", sequenceName = "alcatel.payment_api_recharge_in_recharge_in_id_seq", allocationSize = 1)
    @Column(name = "recharge_in_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "externaldata1")
    private String externalData1;

    @Size(max = 255)
    @Column(name = "externaldata2")
    private String externalData2;

    @Size(max = 255)
    @Column(name = "msisdn")
    private String msisdn;

    @Size(max = 255)
    @Column(name = "refillprofilid")
    private String refillProfilId;

    @Size(max = 255)
    @Column(name = "taxcode")
    private String taxCode;

    @Size(max = 255)
    @Column(name = "transactionamount")
    private String transactionAmount;

    @Size(max = 255)
    @Column(name = "trsid")
    private String trsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operationresponse_response_id")
    private OperationResponse operationResponse;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Size(max = 25)
    @Column(name = "canal", length = 25)
    private String canal;

}
