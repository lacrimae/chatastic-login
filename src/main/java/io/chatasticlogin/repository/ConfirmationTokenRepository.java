package io.chatasticlogin.repository;

import io.chatasticlogin.model.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {

    Optional<ConfirmationToken> findByToken(String token);
}
