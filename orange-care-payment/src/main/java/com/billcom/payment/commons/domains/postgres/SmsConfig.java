package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_api_sms_config", schema = "alcatel")
public class SmsConfig {

    @Id
    @Column(name = "id", nullable = false, precision = 38)
    private Long id;

    @Size(max = 20)
    @NotNull
    @Column(name = "canal", nullable = false, length = 20)
    private String canal;

    @Size(max = 10)
    @NotNull
    @Column(name = "prgcode", nullable = false, length = 10)
    private String prgcode;

    @NotNull
    @Column(name = "enable", nullable = false, precision = 38)
    private int enable;

    @Size(max = 255)
    @Column(name = "text")
    private String text;

    @Size(max = 255)
    @Column(name = "text_cancel_pay")
    private String textCancelPay;

}
