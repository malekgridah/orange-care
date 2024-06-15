package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
