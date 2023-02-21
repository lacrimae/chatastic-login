package io.chatasticauth.service.impl;

import io.chatasticauth.DTO.UserDTO;
import io.chatasticauth.mapper.UserMapper;
import io.chatasticauth.repository.UserRepository;
import io.chatasticauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    @Override
    public UserDTO save(UserDTO dto) {
        var entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toDto(entity);
    }
}
