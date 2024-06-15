package com.billcom.payment.commons.repositories.bscs;

import com.billcom.payment.commons.domains.bscs.FinTrxInterfaceHist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinTrxInterfaceHistRepository extends JpaRepository<FinTrxInterfaceHist, Long> {
    boolean existsByDocumentIdAndAction(Long documentId, String action);
    boolean existsByGeneratedTransactionIdAndAction(Long generatedTransactionId, String action);
    boolean existsByOriginalTransactionIdAndAction(Long originalTransactionId, String action);
    boolean existsByDocumentIdAndCsIdAndUseCaseCode(Long documentId, Long csId, String useCaseCode);

}
