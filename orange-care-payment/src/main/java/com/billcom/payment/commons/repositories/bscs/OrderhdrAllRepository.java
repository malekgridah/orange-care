package com.billcom.payment.commons.repositories.bscs;

import com.billcom.payment.commons.domains.bscs.OrderhdrAll;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderhdrAllRepository extends JpaRepository<OrderhdrAll, Long> {
    OrderhdrAll findByDocumentIdAndStatus(Long documentId, String status);
    OrderhdrAll findByDocumentCodeAndStatus(String documentCode, String status);

    Boolean existsByDocumentCodeAndStatus(String documentCode, String status);

    Boolean existsByDocumentIdAndStatus(Long documentId, String status);

}
