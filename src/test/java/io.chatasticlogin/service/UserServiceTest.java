package io.chatasticlogin.service;

import io.chatasticlogin.DTO.UserDTO;
import io.chatasticlogin.mapper.UserMapper;
import io.chatasticlogin.model.User;
import io.chatasticlogin.repository.UserRepository;
import io.chatasticlogin.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static java.io.chatasticlogin.testutils.TestConstants.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper mapper;
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldSaveUserCorrectly() {
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
        when(mapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        when(mapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        UserDTO userDTO = userService.register(userRequestDTO);

        verify(mapper, times(1)).toEntity(any(UserDTO.class));
        verify(repository, times(1)).save(any(User.class));
        verify(mapper, times(1)).toDto(any(User.class));
        assertEquals(UUID, userDTO.getUuid());
        assertEquals(EMAIL, userDTO.getEmail());
        assertEquals(NICKNAME, userDTO.getNickname());
        assertEquals(FIRST_NAME, userDTO.getFirstName());
        assertEquals(LAST_NAME, userDTO.getLastName());
        assertNull(userDTO.getPassword());
    }

}