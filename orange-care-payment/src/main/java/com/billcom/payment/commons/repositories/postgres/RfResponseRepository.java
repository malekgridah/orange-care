package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.RfResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RfResponseRepository extends JpaRepository<RfResponse, Long> {
}
