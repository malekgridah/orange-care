package com.billcom.payment.commons.domains.bscs;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ORDERHDR_ALL")
public class OrderhdrAll {
    @Id
    @Column(name = "OHXACT", nullable = false)
    private Long documentId;

    @Size(max = 30)
    @Column(name = "OHREFNUM", length = 30)
    private String documentCode;

    @Column(name = "OHREFDATE")
    private LocalDate refDate;

    @Column(name = "OHDUEDATE")
    private LocalDate dueDate;

    @Column(name = "OHCOSTCENT")
    private Long costCenterId;

    @NotNull
    @Column(name = "OHSTATUS", nullable = false)
    private String status;

}
