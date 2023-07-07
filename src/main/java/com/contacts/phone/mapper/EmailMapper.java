package com.contacts.phone.mapper;

import com.contacts.phone.dto.EmailDto;
import com.contacts.phone.entity.Email;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ContactMapper.class})
public interface EmailMapper {
    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);

    Email toEntity(EmailDto emailDto);

    EmailDto toDto(Email email);
}
