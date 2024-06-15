package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.BillingReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingReferenceRepository extends JpaRepository<BillingReference, Long> {
}
