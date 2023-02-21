package io.chatasticauth.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@RedisHash("User")
@Setter
public class User {

    @Id
    private String uuid;

    private String email;
    private String nickname;
    private String firstName;
    private String lastName;
    private String password;
}
