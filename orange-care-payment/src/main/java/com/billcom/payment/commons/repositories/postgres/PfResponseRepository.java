package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.PfResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PfResponseRepository extends JpaRepository<PfResponse, Long> {
}
