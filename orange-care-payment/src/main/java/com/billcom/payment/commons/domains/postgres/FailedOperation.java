package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "payment_api_failed_operation", schema = "alcatel")
public class FailedOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_api_failed_operation_gen")
    @SequenceGenerator(name = "payment_api_failed_operation_gen", sequenceName = "alcatel.payment_api_failed_operation_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;


    @NotNull
    @Column(name = "counter", nullable = false)
    private int counter;

    @Size(max = 255)
    @Column(name = "invoicereference")
    private String invoiceReference;

    @Size(max = 255)
    @Column(name = "statut")
    private String status;

    @Size(max = 255)
    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_pay_id")
    private Pay payPay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rechargein_recharge_in_id")
    private RechargeIn rechargeinRechargeIn;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @OneToMany(mappedBy = "failedOperation")
    private Set<PfResponse> PfResponses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "failedOperation")
    private Set<RfResponse> RfResponses = new LinkedHashSet<>();

}
