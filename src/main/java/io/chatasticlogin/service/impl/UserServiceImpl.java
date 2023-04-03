package io.chatasticlogin.service.impl;

import io.chatasticlogin.DTO.UserDTO;
import io.chatasticlogin.mapper.UserMapper;
import io.chatasticlogin.model.ConfirmationToken;
import io.chatasticlogin.repository.ConfirmationTokenRepository;
import io.chatasticlogin.repository.UserRepository;
import io.chatasticlogin.service.EmailService;
import io.chatasticlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String EMAIL_BODY = "Dear %s,\n\n" +
            "Thank you for registering for our service. To activate your account, please click on the following link:\n\n" +
            "%s\n\n" +
            "This link will expire in 15 minutes.\n\n" +
            "If you did not register for our service, please disregard this email.\n\n" +
            "Sincerely,\n" +
            "The Chatastic Team";


    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    @Override
    public UserDTO register(UserDTO dto) {
        var userExists = userRepository.findByEmail(dto.getEmail());

        if (userExists.isPresent()) {
            throw new IllegalStateException("Email already exists.");
        }

        var entity = mapper.toEntity(dto);
        log.debug("Request to save user with uuid: {}", entity.getUuid());
        userRepository.save(entity);

        // todo: move the logic to aspect
        ConfirmationToken token = ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(entity)
                .build();
        confirmationTokenRepository.save(token);

        String link = "http://localhost:8080/api/v1/registration?token=" + token.getToken();
        emailService.sendVerificationMessage(entity.getEmail(),
                String.format(EMAIL_BODY, entity.getFirstName(), link));

        return mapper.toDto(entity);
    }

    @Override
    public void enable(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
