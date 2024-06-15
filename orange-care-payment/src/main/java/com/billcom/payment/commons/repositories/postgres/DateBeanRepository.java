package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.DateBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateBeanRepository extends JpaRepository<DateBean, Long> {
}
