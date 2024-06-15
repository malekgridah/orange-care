package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.MoneyBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyBeanRepository extends JpaRepository<MoneyBean, Long> {
}
