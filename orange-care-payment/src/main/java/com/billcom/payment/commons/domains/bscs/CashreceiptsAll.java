package com.billcom.payment.commons.domains.bscs;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CASHRECEIPTS_ALL")
public class CashreceiptsAll {

    @Id
    @Column(name = "CAXACT", nullable = false)
    private Double trsId;

    @Column(name = "CUSTOMER_ID")
    private Long csId;

    @Size(max = 30)
    @Column(name = "CACHKNUM", length = 30)
    private String trsRefKey;

    @Column(name = "CATYPE")
    private String trsType;

    @Column(name = "BILLING_ACCOUNT_ID")
    private Long billingAccountId;

    @Column(name = "CATRIGXACT")
    private Long firstTrsId;

}
