package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_api_money_bean", schema = "alcatel")
public class MoneyBean {

    @Id
    @Column(name = "money_bean_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Size(max = 255)
    @Column(name = "currency")
    private String currency;

}
