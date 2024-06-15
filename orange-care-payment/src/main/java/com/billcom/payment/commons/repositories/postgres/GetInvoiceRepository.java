package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.GetInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GetInvoiceRepository extends JpaRepository<GetInvoice, Long> {
}
