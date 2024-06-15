package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.FailedOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedOperationRepository extends JpaRepository<FailedOperation, Long> {

    Page<FailedOperation> findByStatus(Pageable pageable, String status);
}
