package com.billcom.payment.commons.repositories.postgres;


import com.billcom.payment.commons.domains.postgres.AuthentificationTable;
import com.billcom.payment.commons.domains.postgres.AuthentificationTableKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthentificationTableRepository extends JpaRepository<AuthentificationTable, AuthentificationTableKey> {

	
	Optional<AuthentificationTable> findByAuthentificationKey(AuthentificationTableKey key);
}
