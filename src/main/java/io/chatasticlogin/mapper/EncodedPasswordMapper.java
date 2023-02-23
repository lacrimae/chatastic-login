package io.chatasticlogin.mapper;

import io.chatasticlogin.mapper.annotations.EncodedMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public record EncodedPasswordMapper(PasswordEncoder encoder) {

    @EncodedMapping
    public String encode(String password) {
        return encoder.encode(password);
    }
}
