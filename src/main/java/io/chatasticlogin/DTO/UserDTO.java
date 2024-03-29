package io.chatasticlogin.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDTO {

    @JsonView(Views.UserResponseDTO.class)
    private String uuid;

    @JsonView({Views.UserRegistrationRequestDTO.class, Views.UserLoginRequestDTO.class, Views.UserResponseDTO.class})
    private String email;
    @JsonView({Views.UserRegistrationRequestDTO.class, Views.UserResponseDTO.class})
    private String nickname;
    @JsonView({Views.UserRegistrationRequestDTO.class, Views.UserResponseDTO.class})
    private String firstName;
    @JsonView({Views.UserRegistrationRequestDTO.class, Views.UserResponseDTO.class})
    private String lastName;

    @JsonView({Views.UserRegistrationRequestDTO.class, Views.UserLoginRequestDTO.class})
    private String password;
}
