package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.RechargeInParam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RechargeInParamRepository extends JpaRepository<RechargeInParam, Long> {

    Optional<List<RechargeInParam>> findByRechargeId (String rechargeId);


    Optional<RechargeInParam> findFirstByCanal(String canal);
}
