package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayRepository extends JpaRepository<Pay, Long> {

    Optional<Pay> findPayByBtOhxact(Long btOhxact);
    Optional<Pay> findPayByTrsIdAndOperationType(Long trsId, String operationType);
    Optional<Pay> findPayByBtOhxactAndOperationType(Long trsId, String operationType);
    Optional<Pay> findPayByBtOhxactAndOperationTypeAndOperationState(Long btOhxact, String operationType, String OperationType);
}
