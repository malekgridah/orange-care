package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.CustomerReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReferenceRepository extends JpaRepository<CustomerReference, Long> {
}
