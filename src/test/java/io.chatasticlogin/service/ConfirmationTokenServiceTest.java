package io.chatasticlogin.service;

import io.chatasticlogin.exception.EntityNotFoundException;
import io.chatasticlogin.exception.EntityNotValidException;
import io.chatasticlogin.model.ConfirmationToken;
import io.chatasticlogin.model.User;
import io.chatasticlogin.service.impl.ConfirmationTokenServiceImpl;
import io.chatasticlogin.repository.ConfirmationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.io.chatasticlogin.testutils.TestConstants.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ConfirmationTokenServiceTest {

    private final static String TEST_TOKEN = "test token";
    private final static ConfirmationToken CONFIRMATION_TOKEN = ConfirmationToken.builder()
            .token(TEST_TOKEN)
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .user(User.builder().uuid(UUID).build())
            .build();
    private final static ConfirmationToken CONFIRMATION_TOKEN_1 = ConfirmationToken.builder()
            .token(TEST_TOKEN)
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .user(User.builder().uuid(UUID).build())
            .build();

    @InjectMocks
    private ConfirmationTokenServiceImpl confirmationTokenService;
    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Mock
    private UserService userService;

    @Test
    void shouldConfirmTokenSuccessfully() {
        when(confirmationTokenRepository.findByToken(TEST_TOKEN)).thenReturn(Optional.of(CONFIRMATION_TOKEN));

        String confirm = confirmationTokenService.confirm(TEST_TOKEN);

        assertEquals("confirmed", confirm);
    }

    @Test
    void shouldCallConfirmTokenSuccessfully() {
        when(confirmationTokenRepository.findByToken(TEST_TOKEN)).thenReturn(Optional.of(CONFIRMATION_TOKEN_1));

        confirmationTokenService.confirm(TEST_TOKEN);

        verify(confirmationTokenRepository, times(1)).findByToken(TEST_TOKEN);
        verify(confirmationTokenRepository, times(1)).save(CONFIRMATION_TOKEN_1);
        verify(userService, times(1)).enable(CONFIRMATION_TOKEN_1.getUser().getUuid());
    }

    @Test
    void shouldThrowExceptionWhenTokenNotFound() {
        when(confirmationTokenRepository.findByToken(TEST_TOKEN)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> confirmationTokenService.confirm(TEST_TOKEN),
                String.format("Entity not found: %s", TEST_TOKEN));
    }

    @Test
    void shouldThrowExceptionWhenTokenAlreadyConfirmed() {
        CONFIRMATION_TOKEN.setConfirmedAt(LocalDateTime.now().minusMinutes(5));
        when(confirmationTokenRepository.findByToken(TEST_TOKEN)).thenReturn(Optional.of(CONFIRMATION_TOKEN));

        assertThrows(EntityNotValidException.class, () -> confirmationTokenService.confirm(TEST_TOKEN),
                String.format("Token %s already confirmed at %s.", TEST_TOKEN, CONFIRMATION_TOKEN.getConfirmedAt()));
    }

    @Test
    void shouldThrowExceptionWhenTokenExpired() {
        CONFIRMATION_TOKEN.setExpiredAt(LocalDateTime.now().minusMinutes(5));
        when(confirmationTokenRepository.findByToken(TEST_TOKEN)).thenReturn(Optional.of(CONFIRMATION_TOKEN));

        assertThrows(EntityNotValidException.class, () -> confirmationTokenService.confirm(TEST_TOKEN),
                String.format("Token %s expired at %s.", TEST_TOKEN, CONFIRMATION_TOKEN.getExpiredAt()));
    }
}
