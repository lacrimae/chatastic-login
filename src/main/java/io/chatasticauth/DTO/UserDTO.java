package io.chatasticauth.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String uuid;

    private String email;
    private String nickname;
    private String firstName;
    private String lastName;
    private String password;
}
