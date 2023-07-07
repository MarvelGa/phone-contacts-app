package com.contacts.phone.mapper;

import com.contacts.phone.dto.ContactDto;
import com.contacts.phone.dto.ResponseContactDto;
import com.contacts.phone.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    @Mapping(source = "emails", target = "emails")
    @Mapping(source = "phones", target = "phones")
    Contact toEntity(ContactDto contactDto);

    @Mapping(source = "emails", target = "emails")
    @Mapping(source = "phones", target = "phones")
    ContactDto toDto(Contact contact);

    @Mapping(source = "name", target = "name")
    @Mapping(expression = "java(toListEmails(contactDto))", target = "emails")
    @Mapping(expression = "java(toListPhones(contactDto))", target = "phones")
    ResponseContactDto toResponseDto(ContactDto contactDto);

    default List<String> toListEmails(ContactDto contactDto) {
        return contactDto.getEmails().stream()
                .map(el->el.getEmailAddress())
                .toList();
    }

    default List<String> toListPhones(ContactDto contactDto) {
        return contactDto.getPhones().stream()
                .map(el->el.getPhone())
                .toList();
    }
}
