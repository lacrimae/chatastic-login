package io.chatasticlogin.service;

import io.chatasticlogin.DTO.UserDTO;

public interface UserService {

    UserDTO register(UserDTO dto);

    void enable(String id);
}
