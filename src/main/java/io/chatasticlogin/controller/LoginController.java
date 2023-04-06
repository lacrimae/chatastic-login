package io.chatasticlogin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.chatasticlogin.DTO.UserDTO;
import io.chatasticlogin.DTO.Views;
import io.chatasticlogin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/login")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Login", description = "The Login API")
public class LoginController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Login user")
    @JsonView(Views.UserResponseDTO.class)
    public UserDTO login(@RequestBody @JsonView(Views.UserLoginRequestDTO.class) UserDTO userDTO) {
        log.debug(String.format("Login user: %s", userDTO.getEmail()));
        return userService.login(userDTO);
    }
}
