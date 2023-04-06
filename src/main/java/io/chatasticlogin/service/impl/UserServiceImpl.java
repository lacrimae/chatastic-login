package io.chatasticlogin.service.impl;

import io.chatasticlogin.DTO.UserDTO;
import io.chatasticlogin.exception.EntityNotFoundException;
import io.chatasticlogin.exception.EntityNotValidException;
import io.chatasticlogin.mapper.UserMapper;
import io.chatasticlogin.model.ConfirmationToken;
import io.chatasticlogin.model.User;
import io.chatasticlogin.repository.ConfirmationTokenRepository;
import io.chatasticlogin.repository.UserRepository;
import io.chatasticlogin.service.EmailService;
import io.chatasticlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String EMAIL_BODY = """
            Dear %s,

            Thank you for registering for our service. To activate your account, please click on the following link:

            %s

            This link will expire in 15 minutes.

            If you did not register for our service, please disregard this email.

            Sincerely,
            The Chatastic Team
            """;

    @Value("${environment}")
    private String env;


    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder encoder;

    @Override
    public UserDTO login(UserDTO dto) {
        String email = dto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("%s is already registered.", email))
        );

        String encodedPassword = user.getPassword();
        String plaintextPassword = dto.getPassword();

        if (!encoder.matches(plaintextPassword, encodedPassword)) {
            throw new EntityNotValidException("The password you entered is incorrect. Please try again.");
        }

        return mapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDTO register(UserDTO dto) {
        var userExists = userRepository.findByEmail(dto.getEmail());

        if (userExists.isEmpty()) {
            throw new EntityNotValidException(String.format("Email %s does not exist.", dto.getEmail()));
        }

        var entity = mapper.toEntity(dto);
        log.debug("Request to save user with uuid: {}", entity.getUuid());
        userRepository.save(entity);

        ConfirmationToken confirmationToken = saveConfirmationToken(entity);
        sendConfirmationEmail(entity, confirmationToken);

        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public UserDTO enable(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        if (user.isEnabled()) {
            throw new EntityNotValidException(String.format("User is already enabled: %s", id));
        }

        user.setEnabled(true);
        userRepository.save(user);

        return mapper.toDto(user);
    }

    private ConfirmationToken saveConfirmationToken(User user) {
        ConfirmationToken token = ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        confirmationTokenRepository.save(token);
        return token;
    }

    private void sendConfirmationEmail(User user, ConfirmationToken token) {
        String link = String.format("%s/api/v1/registration?token=%s", env, token.getToken());
        emailService.sendVerificationMessage(user.getEmail(),
                String.format(EMAIL_BODY, user.getFirstName(), link));
    }
}
