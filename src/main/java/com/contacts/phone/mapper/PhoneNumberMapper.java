package com.contacts.phone.mapper;

import com.contacts.phone.dto.PhoneNumberDto;
import com.contacts.phone.entity.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ContactMapper.class})
public interface PhoneNumberMapper {

    PhoneNumberMapper INSTANCE = Mappers.getMapper(PhoneNumberMapper.class);

    PhoneNumber toEntity(PhoneNumberDto phoneNumberDto);

    PhoneNumberDto toDto(PhoneNumber phoneNumber);
}
