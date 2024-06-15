package com.billcom.payment.commons.repositories.bscs;

import com.billcom.payment.commons.domains.bscs.FinTrxInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface FinTrxInterfaceRepository extends JpaRepository<FinTrxInterface, Long> {
    @Transactional
    @Modifying
    @Query("""
            update FinTrxInterface f set f.lastModDate = ?1, f.generatedTransactionId = ?2, f.status = ?3
            where f.id = ?4""")
    void updatePaymentSuccessByRequestId(LocalDateTime lastModDate, Long generatedTransactionId, String status, Long id);

    @Transactional
    @Modifying
    @Query("update FinTrxInterface f set f.lastModDate = ?1, f.status = ?2, f.error = ?3 where f.id = ?4")
    void updatePaymentFailedById(LocalDateTime lastModDate, String status, String error, Long id);

    boolean existsByDocumentIdAndAction(Long documentId,String action);
    boolean existsByGeneratedTransactionIdAndAction(Long generatedTransactionId, String action);
    boolean existsByOriginalTransactionIdAndAction(Long originalTransactionId, String action);

    boolean existsByDocumentIdAndCsIdAndUseCaseCode(Long documentId, Long csId, String useCaseCode);

}
