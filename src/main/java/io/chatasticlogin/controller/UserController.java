package io.chatasticlogin.controller;

import io.chatasticlogin.DTO.UserDTO;
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

    @GetMapping(value = "/")
    public String hello() {
        return "Hello World!!";
    }

    @PostMapping
    @Operation(summary = "Register user")
    public UserDTO create(@RequestBody final UserDTO userDTO) {
        log.debug("Create user");
        return userService.save(userDTO);
    }
}
