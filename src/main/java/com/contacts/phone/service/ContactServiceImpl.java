package com.contacts.phone.service;

import com.contacts.phone.dto.ContactDto;
import com.contacts.phone.dto.ResponseContactDto;
import com.contacts.phone.entity.Contact;
import com.contacts.phone.entity.Email;
import com.contacts.phone.entity.PhoneNumber;
import com.contacts.phone.entity.User;
import com.contacts.phone.exception.StatusCodes;
import com.contacts.phone.exception.custom.EntityNotFoundException;
import com.contacts.phone.mapper.ContactMapper;
import com.contacts.phone.repository.ContactRepository;
import com.contacts.phone.repository.EmailRepository;
import com.contacts.phone.repository.PhoneNumberRepository;
import com.contacts.phone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseContactDto addContact(ContactDto contactDto) {
        Optional<User> currentUser = userRepository.findUserById(getCurrentUserId());
        Contact contact = contactMapper.toEntity(contactDto);
        Contact savedContact = contactRepository.save(contact);

        if (currentUser.isPresent()) {
            contact.setUser(currentUser.get());
        }

        for (Email email : savedContact.getEmails()) {
            email.setContact(savedContact);
            emailRepository.save(email);
        }

        for (PhoneNumber phoneNumber : savedContact.getPhones()) {
            phoneNumber.setContact(savedContact);
            phoneNumberRepository.save(phoneNumber);
        }

        ContactDto contactDto1 = contactMapper.toDto(savedContact);

        return contactMapper.toResponseDto(contactDto1);
    }

    @Override
    public ResponseContactDto editContact(String contactId, ContactDto contactDto) {
        Contact existingContact = contactRepository.findById(UUID.fromString(contactId))
                .orElseThrow(() -> new EntityNotFoundException(StatusCodes.ENTITY_NOT_FOUND.name(), String.format("Contact not found with Id %s", contactId)));

        existingContact.setName(contactDto.getName());
        existingContact.setEmails(contactMapper.toEntity(contactDto).getEmails());
        existingContact.setPhones(contactMapper.toEntity(contactDto).getPhones());

        Contact savedContact = contactRepository.save(existingContact);
        ContactDto contactDto1 = contactMapper.toDto(savedContact);
        return contactMapper.toResponseDto(contactDto1);

    }

    @Transactional
    @Override
    public void deleteContact(String contactId) {
        Contact existingContact = contactRepository.findById(UUID.fromString(contactId))
                .orElseThrow(() -> new EntityNotFoundException(StatusCodes.ENTITY_NOT_FOUND.name(), String.format("Contact not found with Id %s", contactId)));

        for (Email email : existingContact.getEmails()) {
            emailRepository.delete(email);
        }

        for (PhoneNumber phoneNumber : existingContact.getPhones()) {
            phoneNumberRepository.delete(phoneNumber);
        }

        contactRepository.delete(existingContact);
    }

    @Transactional
    @Override
    public List<ResponseContactDto> getAllContacts() {
        List<Contact> contacts = (List<Contact>) contactRepository.findAll();
        return contacts.stream()
                .map(contactMapper::toDto)
                .map(contactMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> user = userRepository.findUserByUserName(userDetails.getUsername());
            if (user.isPresent()) {
                return user.get().getId();
            }
        }
        return null;
    }
}
