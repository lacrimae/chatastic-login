package io.chatasticlogin.service.impl;

import io.chatasticlogin.DTO.UserDTO;
import io.chatasticlogin.mapper.UserMapper;
import io.chatasticlogin.repository.UserRepository;
import io.chatasticlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    @Override
    public UserDTO save(UserDTO dto) {
        var entity = mapper.toEntity(dto);
        log.debug("Request to save user with uuid: {}", entity.getUuid());
        repository.save(entity);
        return mapper.toDto(entity);
    }
}
