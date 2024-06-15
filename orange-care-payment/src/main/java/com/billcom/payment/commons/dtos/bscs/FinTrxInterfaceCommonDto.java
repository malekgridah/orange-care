package com.billcom.payment.commons.dtos.bscs;

import com.billcom.payment.commons.domains.bscs.FinTrxInterface;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link FinTrxInterface}
 */

@Builder
@Value
public class FinTrxInterfaceCommonDto implements Serializable {
    Long priority;
    @Size(max = 20)
    String producer;
    @NotNull
    LocalDateTime entryDate;
    LocalDateTime lastModDate;
    @NotNull
    @Size(max = 10)
    String action;
    @NotNull
    @Size(max = 20)
    String useCaseCode;
    @Size(max = 1)
    String suppressAlloc;
    @Size(max = 60)
    String remark;
    Long originalTransactionId;
    Long csId;
    @Size(max = 30)
    String csIdPub;
    Long baId;
    @Size(max = 30)
    String baIdPub;
    @Size(max = 3)
    String paymentMethodCode;
    @Size(max = 20)
    String paymentChannelCode;
    @NotNull
    @Size(max = 30)
    String transactionReference;
    LocalDateTime transactionReferenceDate;
    @NotNull
    Double amount;
    @NotNull
    @Size(max = 3)
    String currencyCode;
    @Size(max = 100)
    String glAccount;
    Long documentId;
    @Size(max = 120)
    String reference1;
    @Size(max = 120)
    String reference2;
    @Size(max = 120)
    String reference3;
    @Size(max = 120)
    String reference4;
    @Size(max = 120)
    String reference5;
    @Size(max = 120)
    String reference6;
    @Size(max = 120)
    String reference7;
    @Size(max = 120)
    String reference8;
    @Size(max = 120)
    String reference9;
    @Size(max = 120)
    String reference10;
    @Size(max = 120)
    String reference11;
    @Size(max = 120)
    String reference12;
    @Size(max = 120)
    String reference13;
    @Size(max = 120)
    String reference14;
    @Size(max = 120)
    String reference15;
    @Size(max = 120)
    String reference16;
    @Size(max = 120)
    String reference17;
    @Size(max = 120)
    String reference18;
    @Size(max = 120)
    String reference19;
    @Size(max = 120)
    String reference20;
    @Size(max = 2)
    String status;
    @Size(max = 500)
    String error;
    Long generatedTransactionId;
}
