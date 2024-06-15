package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_api_recharge_in_param", schema = "alcatel")
public class RechargeInParam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_api_recharge_in_param_gen")
    @SequenceGenerator(name = "payment_api_recharge_in_param_gen", sequenceName = "alcatel.payment_api_recharge_in_param_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 64)
    @NotNull
    @Column(name = "recharge_id", nullable = false, length = 64)
    private String rechargeId;

    @Size(max = 64)
    @NotNull
    @Column(name = "refill_profil_id", nullable = false, length = 64)
    private String refillProfilId;

    @Size(max = 64)
    @NotNull
    @Column(name = "min_transaction_amount", nullable = false, length = 64)
    private String minTransactionAmount;

    @Size(max = 64)
    @NotNull
    @Column(name = "max_transaction_amount", nullable = false, length = 64)
    private String maxTransactionAmount;

    @Column(name = "canal")
    private String canal;

}
