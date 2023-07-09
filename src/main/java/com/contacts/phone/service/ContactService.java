package com.contacts.phone.service;

import com.contacts.phone.dto.ContactDto;
import com.contacts.phone.dto.ResponseContactDto;

import java.util.List;

public interface ContactService {
    ResponseContactDto addContact(ContactDto contactDto);

    ResponseContactDto editContact(String contactId, ContactDto contactDto);

    void deleteContact(String contactId);

    List<ResponseContactDto> getAllContacts();

    List<ResponseContactDto> getAllContactsByUserId();

}
