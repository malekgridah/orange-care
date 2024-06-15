package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.SmsConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsConfigRepository extends JpaRepository<SmsConfig, Long> {

    SmsConfig findFirstByCanalAndPrgcode(String canal, String prgCode);

    boolean existsByCanal(String canal);



}
