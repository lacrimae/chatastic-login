package io.chatasticauth.mapper;

import io.chatasticauth.DTO.UserDTO;
import io.chatasticauth.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = EncodedPasswordMapper.class)
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    @Override
    User toEntity(UserDTO dto);

    @Mapping(target = "password", ignore = true)
    @Override
    UserDTO toDto(User entity);
}
