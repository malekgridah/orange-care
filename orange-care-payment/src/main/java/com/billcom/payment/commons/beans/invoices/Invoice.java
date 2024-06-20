package com.billcom.payment.commons.beans.invoices;

import com.billcom.payment.commons.beans.Money;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Invoice {
    private String documentId;
    private String documentCode;
    private String status;
    private Money billedAmount;
    private Money openAmount;
    private LocalDateTime entryDate;
    private LocalDate dueDate;
    private LocalDate refDate;
}
