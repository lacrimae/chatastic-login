package io.chatasticlogin.service.impl;

import io.chatasticlogin.DTO.UserDTO;
import io.chatasticlogin.mapper.UserMapper;
import io.chatasticlogin.repository.UserRepository;
import io.chatasticlogin.service.UserService;
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
