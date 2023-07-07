package com.contacts.phone;

import com.contacts.phone.dto.ContactDto;
import com.contacts.phone.dto.ResponseContactDto;
import com.contacts.phone.entity.Contact;
import com.contacts.phone.entity.Email;
import com.contacts.phone.entity.PhoneNumber;
import com.contacts.phone.entity.User;
import com.contacts.phone.exception.custom.EntityNotFoundException;
import com.contacts.phone.mapper.ContactMapper;
import com.contacts.phone.repository.ContactRepository;
import com.contacts.phone.repository.EmailRepository;
import com.contacts.phone.repository.PhoneNumberRepository;
import com.contacts.phone.repository.UserRepository;
import com.contacts.phone.service.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith({MockitoExtension.class})
public class ContactServiceImplTest {
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    @Test
    public void testAddContact() {
        // Given
        Contact contact = new Contact();
        contact.setName("John Doe");

        Email email1 = new Email();
        email1.setEmailAddress("john.doe@example.com");
        email1.setContact(contact);

        Email email2 = new Email();
        email2.setEmailAddress("johndoe@gmail.com");
        email2.setContact(contact);

        contact.getEmails().add(email1);
        contact.getEmails().add(email2);

        PhoneNumber phoneNumber1 = new PhoneNumber();
        phoneNumber1.setPhone("+1234567890");
        phoneNumber1.setContact(contact);

        PhoneNumber phoneNumber2 = new PhoneNumber();
        phoneNumber2.setPhone("+9876543210");
        phoneNumber2.setContact(contact);

        contact.getPhones().add(phoneNumber1);
        contact.getPhones().add(phoneNumber2);

        UUID userId = UUID.randomUUID();

        ContactDto contactDto = new ContactDto();
        contactDto.setName("John Doe");

        User user = new User();
        user.setId(userId);

        Email email = new Email();

        Optional<User> currentUser = Optional.of(user);
        UserDetails userDetails = User.builder()
                .login("testuser")
                .password("testpassword")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = mock(SecurityContext.class);

        ResponseContactDto expectedResponseDto = new ResponseContactDto();

        // When
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findUserById(any(UUID.class))).thenReturn(currentUser);
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);
        when(emailRepository.save(any(Email.class))).thenReturn(email);
        when(phoneNumberRepository.save(any(PhoneNumber.class))).thenReturn(new PhoneNumber());
        when(contactMapper.toEntity(any(ContactDto.class))).thenReturn(contact);
        when(contactMapper.toDto(any(Contact.class))).thenReturn(new ContactDto());
        when(contactMapper.toResponseDto(any(ContactDto.class))).thenReturn(new ResponseContactDto());
        when(userRepository.findUserByLogin(any(String.class))).thenReturn(currentUser);

        // Act
        ResponseContactDto result = contactService.addContact(contactDto);

        // Assert
        assertEquals(expectedResponseDto, result);
        verify(userRepository, times(1)).findUserById(userId);
        verify(contactRepository, times(1)).save(any(Contact.class));
        verify(contactMapper, times(1)).toDto(any(Contact.class));
        verify(contactMapper, times(1)).toResponseDto(any(ContactDto.class));
    }

    @Test
    public void testEditContact() {
        // Mock input data
        String contactId = "f4ad43b3-6416-4c59-92b7-e4de7ac02ba6";
        ContactDto contactDto = new ContactDto();
        contactDto.setName("John Doe");

        // Mock existing contact
        Contact existingContact = new Contact();
        existingContact.setId(UUID.fromString(contactId));

        // Mock saved contact after modification
        Contact savedContact = new Contact();
        savedContact.setId(UUID.fromString(contactId));
        savedContact.setName("John Doe");

        // Mock mapping
        Mockito.when(contactRepository.findById(UUID.fromString(contactId))).thenReturn(Optional.of(existingContact));
        Mockito.when(contactMapper.toEntity(contactDto)).thenReturn(savedContact);
        Mockito.when(contactRepository.save(savedContact)).thenReturn(savedContact);
        Mockito.when(contactMapper.toDto(savedContact)).thenReturn(contactDto);
        Mockito.when(contactMapper.toResponseDto(contactDto)).thenReturn(new ResponseContactDto());

        // Call the method under test
        ResponseContactDto result = contactService.editContact(contactId, contactDto);

        // Verify the interactions and assertions
        Mockito.verify(contactRepository).findById(UUID.fromString(contactId));
        Mockito.verify(contactRepository).save(savedContact);

        // Assertions
        assertNotNull(result);
    }

    @Test
    public void testDeleteContact() {
        // Prepare test data
        String contactId = "f4ad43b3-6416-4c59-92b7-e4de7ac02ba6";
        Contact existingContact = new Contact();
        existingContact.setId(UUID.fromString(contactId));
        existingContact.setEmails(new HashSet<>());
        existingContact.setPhones(new HashSet<>());

        // Mock the repository methods
        Mockito.when(contactRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(existingContact));

        // Call the service method
        contactService.deleteContact(contactId);

        // Verify the repository method calls
        Mockito.verify(contactRepository, Mockito.times(1)).delete(existingContact);
        Mockito.verify(emailRepository, Mockito.times(0)).delete(Mockito.any(Email.class));
        Mockito.verify(phoneNumberRepository, Mockito.times(0)).delete(Mockito.any(PhoneNumber.class));
    }

    @Test
    public void testDeleteContact_EntityNotFound() {
        // Prepare test data
        String contactId = "f4ad43b3-6416-4c59-92b7-e4de7ac02ba6";

        // Mock the repository methods
        Mockito.when(contactRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        // Call the service method and assert that EntityNotFoundException is thrown
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            contactService.deleteContact(contactId);
        });

        // Verify the exception message
        String expectedMessage = String.format("Contact not found with Id %s", contactId);
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGetAllContacts() {
        // Prepare test data
        Email email1 = new Email();
        email1.setEmailAddress("john.doe@example.com");


        Email email2 = new Email();
        email2.setEmailAddress("johndoe@gmail.com");

        PhoneNumber phoneNumber1 = new PhoneNumber();
        phoneNumber1.setPhone("+1234567890");

        PhoneNumber phoneNumber2 = new PhoneNumber();
        phoneNumber2.setPhone("+9876543210");

        Contact contact1 = new Contact();
        contact1.setId(UUID.randomUUID());
        contact1.setName("John Doe");
        contact1.setEmails(new HashSet<>(Arrays.asList(email1, email2)));
        contact1.setPhones(new HashSet<>(Arrays.asList(phoneNumber1, phoneNumber2)
        ));

        email1.setContact(contact1);
        email2.setContact(contact1);
        phoneNumber1.setContact(contact1);
        phoneNumber2.setContact(contact1);

        Contact contact2 = new Contact();
        contact2.setId(UUID.randomUUID());
        contact2.setName("Jane Smith");
        contact1.setEmails(new HashSet<>(Arrays.asList(email1, email2)));
        contact2.setPhones(new HashSet<>(Arrays.asList(phoneNumber1, phoneNumber2
        )));

        List<Contact> mockContacts = Arrays.asList(contact1, contact2);

        Mockito.when(contactRepository.findAll()).thenReturn(mockContacts);

        List<ResponseContactDto> result = contactService.getAllContacts();

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());
    }

}

