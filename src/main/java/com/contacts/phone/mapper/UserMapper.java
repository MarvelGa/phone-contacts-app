package com.contacts.phone.mapper;

import com.contacts.phone.dto.UserDto;
import com.contacts.phone.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto userDto);

    @Mappings({@Mapping(target = "password", ignore = true)})
    UserDto toDto(User user);

}
