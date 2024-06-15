package com.billcom.payment.commons.domains.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_api_user", schema = "alcatel")
public class User {

    @Id
    @Size(max = 255)
    @Column(name = "login", nullable = false)
    private String login;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Integer enabled;

}
