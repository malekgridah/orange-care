package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payment_api_get_invoices", schema = "alcatel")
public class GetInvoice {
    @Id
    @Column(name = "get_invoices_id", nullable = false)
    private Long id;

    @Column(name = "enddate")
    private Instant endDate;

    @Column(name = "startdate")
    private Instant startDate;

    @Column(name = "trsid")
    private Long trsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baref_billing_acount_id")
    private BillingReference barefBillingAcount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custref_customer_id")
    private CustomerReference custRefCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operationresponse_response_id")
    private OperationResponse operationResponse;

}
