package io.chatasticlogin.service.impl;

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
    public ConfirmationToken get(String token) {
        return repository.findByToken(token)
                // todo: introduce app-specific Exceptions
                .orElseThrow(() -> new IllegalStateException("Token not found"));
    }

    @Override
    public void save(ConfirmationToken token) {
        repository.save(token);
    }

    @Override
    public String confirm(String token) {
        ConfirmationToken confirmationToken = get(token);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Token already confirmed!");
        }

        if (confirmationToken.getExpiredAt()
                .isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        repository.save(confirmationToken);
        userService.enable(confirmationToken.getUser().getUuid());

        return "confirmed";
    }
}
