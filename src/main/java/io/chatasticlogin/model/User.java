package io.chatasticlogin.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@RedisHash("user")
@Setter
public class User {

    @Id
    private String uuid;

    @Indexed
    private String email;
    private String nickname;
    private String firstName;
    private String lastName;
    private String password;
    private boolean enabled = false;
}
