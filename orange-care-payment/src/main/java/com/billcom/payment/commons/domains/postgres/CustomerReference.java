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
@Table(name = "payment_api_customer_reference", schema = "alcatel")
public class CustomerReference {
    @Id
    @Column(name = "customer_id", nullable = false)
    private Long id;

    @Column(name = "csid")
    private Long csId;

    @Size(max = 255)
    @Column(name = "csidpub")
    private String csIdPub;

}
