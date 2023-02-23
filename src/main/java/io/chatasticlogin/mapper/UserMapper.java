package io.chatasticlogin.mapper;

import io.chatasticlogin.DTO.UserDTO;
import io.chatasticlogin.mapper.annotations.EncodedMapping;
import io.chatasticlogin.mapper.annotations.UUIDGenerator;
import io.chatasticlogin.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EncodedPasswordMapper.class, UUIDMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "uuid", qualifiedBy = UUIDGenerator.class)
    @Override
    User toEntity(UserDTO dto);

    @Override
    UserDTO toDto(User entity);
}
