package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.TrueFalseConverter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment_api_pay", schema = "alcatel")
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_api_pay_id_gen")
    @SequenceGenerator(name = "payment_api_pay_id_gen", sequenceName = "alcatel.payment_api_pay_pay_id_seq", allocationSize = 1)
    @Column(name = "pay_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "bankname")
    private String bankName;

    @Column(name = "btcaxact")
    private Long btCaxact;

    @Column(name = "btohxact")
    private Long btOhxact;

    @Column(name = "csid")
    private Long csId;

    @Size(max = 255)
    @Column(name = "csidpub")
    private String csIdPub;

    @Size(max = 255)
    @Column(name = "cspaccno")
    private String cspAccNo;

    @Size(max = 255)
    @Column(name = "cspaccowner")
    private String cspAccOwner;

    @Size(max = 255)
    @Column(name = "cspbankcode")
    private String cspBankCode;

    @Size(max = 255)
    @Column(name = "glacash")
    private String glaCash;

    @Size(max = 255)
    @Column(name = "gladis")
    private String glaDis;

    @Column(name = "paymentcurrencyid")
    private Long paymentCurrencyId;

    @Size(max = 255)
    @Column(name = "paymentmode")
    private String paymentMode;

    @Size(max = 255)
    @Column(name = "rtcachknum")
    private String rtCachknum;

    @Size(max = 255)
    @Column(name = "rtcarem")
    private String rtCarem;

    @Size(max = 255)
    @Column(name = "rtcausername")
    private String rtCauserName;

    @Column(name = "rtexchangeratecurrency")
    private Long rtExchangeRateCurrency;

    @Size(max = 255)
    @Column(name = "rtfccodepay")
    private String rtFcCodePay;

    @Column(name = "synchronousmode")
    @Convert(converter = TrueFalseConverter.class)
    private Boolean synchronousMode;

    @Size(max = 255)
    @Column(name = "transxcode")
    private String transxCode;

    @Column(name = "trsid")
    private Long trsId;

    @Size(max = 255)
    @Column(name = "validthroughdate")
    private String validThroughDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operationresponse_response_id")
    private OperationResponse operationResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rtcachkamtpay_money_bean_id")
    private MoneyBean rtcachkamtpayMoneyBean;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rtcachkdate_date_bean_id")
    private DateBean rtcachkdateDateBean;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rtcadisamtpay_money_bean_id")
    private MoneyBean rtcadisamtpayMoneyBean;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rtcarecdate_date_bean_id")
    private DateBean rtcarecdateDateBean;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "canal")
    private String canal;

    @Size(max = 50)
    @Column(name = "operationtype", length = 50)
    private String operationType;

    @Size(max = 50)
    @Column(name = "operationstate", length = 50)
    private String operationState;

}
