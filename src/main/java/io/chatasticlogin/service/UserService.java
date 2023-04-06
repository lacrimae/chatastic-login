package io.chatasticlogin.service;

import io.chatasticlogin.DTO.UserDTO;

public interface UserService {

    UserDTO login(UserDTO dto);

    UserDTO register(UserDTO dto);

    UserDTO enable(String id);
}
