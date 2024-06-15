package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payment_api_date_bean", schema = "alcatel")
public class DateBean {
    @Id
    @Column(name = "date_bean_id", nullable = false)
    private Long id;

    @Column(name = "datetime")
    private Instant dateTime;

}
