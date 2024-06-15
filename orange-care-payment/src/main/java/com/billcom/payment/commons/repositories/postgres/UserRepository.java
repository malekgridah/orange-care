package com.billcom.payment.commons.repositories.postgres;

import com.billcom.payment.commons.domains.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByLogin(String login);

}
