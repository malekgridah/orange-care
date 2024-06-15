package com.billcom.payment.commons.repositories.bscs;

import com.billcom.payment.commons.domains.bscs.CashreceiptsAll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashreceiptsAllRepository extends JpaRepository<CashreceiptsAll, Double> {

    boolean existsByTrsRefKeyAndTrsType(String trsRefKey, String trsType);

    boolean existsByTrsIdAndTrsType(Double trsId, String trsType);

    Optional<CashreceiptsAll> findByTrsRefKeyAndTrsType(String trsRefKey, String trsType);

    Optional<CashreceiptsAll> findByTrsIdAndTrsType(Double trsId, String trsType);



}
