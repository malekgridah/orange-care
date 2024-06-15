package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.OperationResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationResponseRepository extends JpaRepository<OperationResponse, Long> {
}
