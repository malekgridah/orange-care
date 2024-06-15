package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.RechargeIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RechargeInRepository extends JpaRepository<RechargeIn, Long> {

    @Query(value = "select r.* from alcatel.payment_api_recharge_in r " +
            "LEFT join alcatel.payment_api_failed_operation fo on r.recharge_in_id = fo.rechargein_recharge_in_id " +
            "LEFT join alcatel.payment_api_operation_response opr on r.operationresponse_response_id = opr.response_id " +
            "where r.msisdn = :msisdn and (opr.issuccessful = 'T' or (opr.issuccessful = 'F' and fo.statut <> 'FINISHED_WITH_ERROR')) " +
            "and DATE(r.entry_date) = DATE(NOW())", nativeQuery = true)
    List<RechargeIn> countMadeTransactionsByMsisdn(@Param("msisdn") String msisdn);

    Optional<RechargeIn> findFirstByTrsId(String trsId);
}
