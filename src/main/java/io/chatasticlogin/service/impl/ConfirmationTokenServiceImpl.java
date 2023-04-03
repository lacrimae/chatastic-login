package io.chatasticlogin.service.impl;

import io.chatasticlogin.exception.EntityNotFoundException;
import io.chatasticlogin.exception.EntityNotValidException;
import io.chatasticlogin.model.ConfirmationToken;
import io.chatasticlogin.repository.ConfirmationTokenRepository;
import io.chatasticlogin.service.ConfirmationTokenService;
import io.chatasticlogin.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;
    private final UserService userService;

    @Override
    public void save(ConfirmationToken token) {
        repository.save(token);
    }

    @Override
    public String confirm(String token) {
        ConfirmationToken confirmationToken = repository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException(token));

        LocalDateTime confirmedAt = confirmationToken.getConfirmedAt();
        if (confirmedAt != null) {
            throw new EntityNotValidException(String.format("Token %s already confirmed at %s.", token, confirmedAt));
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new EntityNotValidException(String.format("Token %s expired at %s.", token, expiredAt));
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        repository.save(confirmationToken);
        userService.enable(confirmationToken.getUser().getUuid());

        return "confirmed";
    }
}
