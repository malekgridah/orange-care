package com.billcom.payment.commons.repositories.bscs;

import com.billcom.payment.commons.domains.bscs.FinHandlingReason;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinHandlingReasonRepository extends JpaRepository<FinHandlingReason, Long> {

    Optional<FinHandlingReasonRepository> findByHandlingReasonIdPub(String handlingReasonIdPub);
}
