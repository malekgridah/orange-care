package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_api_biiling_reference", schema = "alcatel")
public class BillingReference {
    @Id
    @Column(name = "billing_acount_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "billingaccountcode")
    private String billingAccountCode;

    @Column(name = "billingaccountid")
    private Long billingAccountId;

}
