package io.chatasticlogin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.chatasticlogin.DTO.UserDTO;
import io.chatasticlogin.DTO.Views;
import io.chatasticlogin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "User", description = "The User API")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Register user")
    @JsonView(Views.UserResponseDTO.class)
    public UserDTO create(@RequestBody @JsonView(Views.UserRequestDTO.class) UserDTO userDTO) {
        log.debug("Register user");
        return userService.save(userDTO);
    }

    @GetMapping(value = "/")
    public String hello() {
        return "Hello World!!";
    }
}
