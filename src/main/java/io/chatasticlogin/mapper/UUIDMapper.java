package io.chatasticlogin.mapper;

import io.chatasticlogin.mapper.annotations.UUIDGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDMapper {

    @UUIDGenerator
    public String generate(String uuid) {
        return UUID.randomUUID().toString();
    }
}
