package io.chatasticlogin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.chatasticlogin.DTO.UserDTO;
import io.chatasticlogin.DTO.Views;
import io.chatasticlogin.service.ConfirmationTokenService;
import io.chatasticlogin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/registration")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "User", description = "The User API")
public class RegistrationController {

    private final UserService userService;
    private final ConfirmationTokenService tokenService;

    @PostMapping
    @Operation(summary = "Register user")
    @JsonView(Views.UserResponseDTO.class)
    public UserDTO register(@RequestBody @JsonView(Views.UserRequestDTO.class) UserDTO userDTO) {
        log.debug("Register user");
        return userService.register(userDTO);
    }

    @GetMapping()
    public String confirm(@RequestParam("token") String token) {
        return tokenService.confirm(token);
    }
}
