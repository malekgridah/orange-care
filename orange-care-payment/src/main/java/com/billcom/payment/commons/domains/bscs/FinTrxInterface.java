package com.billcom.payment.commons.domains.bscs;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "FIN_TRX_INTERFACE_BCC_BKP", schema = "ERICSSON")
public class FinTrxInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fin_trx_interface_gen")
    @SequenceGenerator(name = "fin_trx_interface_gen", sequenceName = "ERICSSON.FIN_TRX_INTERFACE_REQUEST_ID_SEQ", allocationSize = 1)
    @Column(name = "REQUEST_ID", nullable = false)
    private Long id;

    @Column(name = "PRIORITY")
    private Long priority;

    @Size(max = 20)
    @Column(name = "PRODUCER", length = 20)
    private String producer;

    @NotNull
    @Column(name = "ENTRY_DATE", nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "LAST_MOD_DATE")
    private LocalDateTime lastModDate;

    @Size(max = 10)
    @NotNull
    @Column(name = "ACTION", nullable = false, length = 10)
    private String action;

    @Size(max = 20)
    @NotNull
    @Column(name = "USE_CASE_CODE", nullable = false, length = 20)
    private String useCaseCode;

    @Size(max = 1)
    @Column(name = "SUPPRESS_ALLOC", length = 1)
    private String suppressAlloc;

    @Size(max = 60)
    @Column(name = "REMARK", length = 60)
    private String remark;

    @Column(name = "ORIGINAL_TRANSACTION_ID")
    private Long originalTransactionId;

    @Column(name = "CS_ID")
    private Long csId;

    @Size(max = 30)
    @Column(name = "CS_ID_PUB", length = 30)
    private String csIdPub;

    @Column(name = "BA_ID")
    private Long baId;

    @Size(max = 30)
    @Column(name = "BA_ID_PUB", length = 30)
    private String baIdPub;

    @Size(max = 3)
    @Column(name = "PAYMENT_METHOD_CODE", length = 3)
    private String paymentMethodCode;

    @Size(max = 20)
    @Column(name = "PAYMENT_CHANNEL_CODE", length = 20)
    private String paymentChannelCode;

    @Size(max = 30)
    @NotNull
    @Column(name = "TRANSACTION_REFERENCE", nullable = false, length = 30)
    private String transactionReference;

    @Column(name = "TRANSACTION_REFERENCE_DATE")
    private LocalDateTime transactionReferenceDate;

    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @Size(max = 3)
    @NotNull
    @Column(name = "CURRENCY_CODE", nullable = false, length = 3)
    private String currencyCode;

    @Size(max = 100)
    @Column(name = "GL_ACCOUNT", length = 100)
    private String glAccount;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @Size(max = 120)
    @Column(name = "REFERENCE_1", length = 120)
    private String reference1;

    @Size(max = 120)
    @Column(name = "REFERENCE_2", length = 120)
    private String reference2;

    @Size(max = 120)
    @Column(name = "REFERENCE_3", length = 120)
    private String reference3;

    @Size(max = 120)
    @Column(name = "REFERENCE_4", length = 120)
    private String reference4;

    @Size(max = 120)
    @Column(name = "REFERENCE_5", length = 120)
    private String reference5;

    @Size(max = 120)
    @Column(name = "REFERENCE_6", length = 120)
    private String reference6;

    @Size(max = 120)
    @Column(name = "REFERENCE_7", length = 120)
    private String reference7;

    @Size(max = 120)
    @Column(name = "REFERENCE_8", length = 120)
    private String reference8;

    @Size(max = 120)
    @Column(name = "REFERENCE_9", length = 120)
    private String reference9;

    @Size(max = 120)
    @Column(name = "REFERENCE_10", length = 120)
    private String reference10;

    @Size(max = 120)
    @Column(name = "REFERENCE_11", length = 120)
    private String reference11;

    @Size(max = 120)
    @Column(name = "REFERENCE_12", length = 120)
    private String reference12;

    @Size(max = 120)
    @Column(name = "REFERENCE_13", length = 120)
    private String reference13;

    @Size(max = 120)
    @Column(name = "REFERENCE_14", length = 120)
    private String reference14;

    @Size(max = 120)
    @Column(name = "REFERENCE_15", length = 120)
    private String reference15;

    @Size(max = 120)
    @Column(name = "REFERENCE_16", length = 120)
    private String reference16;

    @Size(max = 120)
    @Column(name = "REFERENCE_17", length = 120)
    private String reference17;

    @Size(max = 120)
    @Column(name = "REFERENCE_18", length = 120)
    private String reference18;

    @Size(max = 120)
    @Column(name = "REFERENCE_19", length = 120)
    private String reference19;

    @Size(max = 120)
    @Column(name = "REFERENCE_20", length = 120)
    private String reference20;

    @Size(max = 2)
    @Column(name = "STATUS", length = 2)
    private String status;

    @Size(max = 500)
    @Column(name = "ERROR", length = 500)
    private String error;

    @Column(name = "GENERATED_TRANSACTION_ID")
    private Long generatedTransactionId;

}
