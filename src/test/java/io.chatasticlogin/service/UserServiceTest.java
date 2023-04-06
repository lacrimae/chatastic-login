package io.chatasticlogin.service;

import io.chatasticlogin.DTO.UserDTO;
import io.chatasticlogin.exception.EntityNotFoundException;
import io.chatasticlogin.exception.EntityNotValidException;
import io.chatasticlogin.mapper.UserMapper;
import io.chatasticlogin.model.ConfirmationToken;
import io.chatasticlogin.model.User;
import io.chatasticlogin.repository.ConfirmationTokenRepository;
import io.chatasticlogin.repository.UserRepository;
import io.chatasticlogin.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static java.io.chatasticlogin.testutils.TestConstants.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    private final static UserDTO LOGIN_USER_DTO = UserDTO.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    private final static UserDTO REGISTER_USER_DTO = UserDTO.builder()
            .email(EMAIL)
            .nickname(NICKNAME)
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .password(PASSWORD)
            .build();

    private final static User LOGIN_USER = User.builder().password(ENCODED_PASSWORD).build();

    @Mock
    private UserMapper mapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loginSuccessfully() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(LOGIN_USER));
        when(encoder.matches(LOGIN_USER_DTO.getPassword(), ENCODED_PASSWORD)).thenReturn(true);
        when(mapper.toDto(LOGIN_USER)).thenReturn(LOGIN_USER_DTO);

        UserDTO actual = userService.login(LOGIN_USER_DTO);

        assertNotNull(actual);
        assertEquals(LOGIN_USER_DTO.getEmail(), actual.getEmail());
    }

    @Test
    void loginShouldThrowExceptionEmailNotFound() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.login(LOGIN_USER_DTO),
                String.format("Entity not found: %s", EMAIL));
    }

    @Test
    void loginShouldThrowExceptionInvalidPassword() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(LOGIN_USER));
        when(encoder.matches(LOGIN_USER_DTO.getPassword(), ENCODED_PASSWORD)).thenReturn(false);

        assertThrows(EntityNotValidException.class, () -> userService.login(LOGIN_USER_DTO),
                "The password you entered is incorrect. Please try again.");
    }

    @Test
    void registerSuccessfully() {
        UserDTO userRequestDTO = UserDTO.builder()
                .email(EMAIL)
                .nickname(NICKNAME)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .build();
        User user = User.builder()
                .uuid(UUID)
                .nickname(NICKNAME)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .build();
        UserDTO userResponseDTO = UserDTO.builder()
                .uuid(UUID)
                .email(EMAIL)
                .nickname(NICKNAME)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(mapper.toEntity(userRequestDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(confirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(any());
        when(mapper.toDto(user)).thenReturn(userResponseDTO);

        UserDTO userDTO = userService.register(userRequestDTO);

        verify(mapper, times(1)).toEntity(any(UserDTO.class));
        verify(userRepository, times(1)).save(any(User.class));
        verify(mapper, times(1)).toDto(any(User.class));
        assertEquals(UUID, userDTO.getUuid());
        assertEquals(EMAIL, userDTO.getEmail());
        assertEquals(NICKNAME, userDTO.getNickname());
        assertEquals(FIRST_NAME, userDTO.getFirstName());
        assertEquals(LAST_NAME, userDTO.getLastName());
        assertNull(userDTO.getPassword());
    }

    @Test
    void registerShouldThrowExceptionEmailExists() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(EntityNotValidException.class, () -> userService.register(REGISTER_USER_DTO),
                String.format("Email %s does not exist.", REGISTER_USER_DTO.getEmail()));
    }

    @Test
    void enableCorrectly() {
        User user = User.builder()
                .uuid(UUID)
                .enabled(false)
                .build();
        when(userRepository.findById(UUID)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.enable(UUID);

        assertTrue(user.isEnabled());
    }

    @Test
    void enableShouldThrowExceptionUserNotFound() {
        when(userRepository.findById(UUID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.enable(UUID),
                String.format("Entity not found: %s", UUID));
    }

    @Test
    void enableShouldThrowExceptionUserAlreadyEnabled() {
        User user = User.builder()
                .uuid(UUID)
                .enabled(true)
                .build();
        when(userRepository.findById(UUID)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        assertThrows(EntityNotValidException.class, () -> userService.enable(UUID),
                String.format("User is already enabled: %s", UUID));
    }
}