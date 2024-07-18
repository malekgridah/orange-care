package com.billcom.payment.commons.beans.invoices.search;

import com.billcom.payment.commons.beans.Money;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Invoice {
    private Long documentId;
    private String documentCode;
    private String status;
    private Integer statusId;
    private Boolean isPaid;
    private Boolean isReversed;
    private Money billedAmount;
    private Money openAmount;
    private LocalDateTime entryDate;
    private LocalDate dueDate;
    private LocalDate refDate;
}
