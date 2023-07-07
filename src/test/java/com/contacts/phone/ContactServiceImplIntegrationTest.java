package com.contacts.phone;

import com.contacts.phone.dto.ContactDto;
import com.contacts.phone.dto.EmailDto;
import com.contacts.phone.dto.PhoneNumberDto;
import com.contacts.phone.dto.ResponseContactDto;
import com.contacts.phone.entity.Contact;
import com.contacts.phone.entity.Email;
import com.contacts.phone.entity.PhoneNumber;
import com.contacts.phone.entity.User;
import com.contacts.phone.repository.ContactRepository;
import com.contacts.phone.repository.EmailRepository;
import com.contacts.phone.repository.PhoneNumberRepository;
import com.contacts.phone.repository.UserRepository;
import com.contacts.phone.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ContactServiceImplIntegrationTest {
    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Test
    public void testGetAllContacts() {
        // Prepare test data
        Contact contact1 = new Contact();
        contact1.setId(UUID.randomUUID());
        contact1.setName("John Doe");

        Contact contact2 = new Contact();
        contact2.setId(UUID.randomUUID());
        contact2.setName("Jane Smith");

        contactRepository.save(contact1);
        contactRepository.save(contact2);

        // Call the service method
        List<ResponseContactDto> result = contactService.getAllContacts();

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    public void testDeleteContact() {
        // Prepare test data
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID());
        contact.setName("John Doe");

        Email email1 = new Email();
        email1.setId(UUID.randomUUID());
        email1.setEmailAddress("john.doe@example.com");
        email1.setContact(contact);

        Email email2 = new Email();
        email2.setId(UUID.randomUUID());
        email2.setEmailAddress("johndoe@gmail.com");
        email2.setContact(contact);

        PhoneNumber phoneNumber1 = new PhoneNumber();
        phoneNumber1.setId(UUID.randomUUID());
        phoneNumber1.setPhone("+1234567890");
        phoneNumber1.setContact(contact);

        PhoneNumber phoneNumber2 = new PhoneNumber();
        phoneNumber2.setId(UUID.randomUUID());
        phoneNumber2.setPhone("+9876543210");
        phoneNumber2.setContact(contact);

        contact.getEmails().add(email1);
        contact.getEmails().add(email2);
        contact.getPhones().add(phoneNumber1);
        contact.getPhones().add(phoneNumber2);

        Contact savedContact = contactRepository.save(contact);

        // Call the service method
        contactService.deleteContact(String.valueOf(savedContact.getId()));

        // Verify the deletion
        Optional<Contact> deletedContact = contactRepository.findById(contact.getId());
        assertFalse(deletedContact.isPresent());

        Optional<Email> deletedEmail1 = emailRepository.findById(email1.getId());
        assertFalse(deletedEmail1.isPresent());

        Optional<Email> deletedEmail2 = emailRepository.findById(email2.getId());
        assertFalse(deletedEmail2.isPresent());

        Optional<PhoneNumber> deletedPhoneNumber1 = phoneNumberRepository.findById(phoneNumber1.getId());
        assertFalse(deletedPhoneNumber1.isPresent());

        Optional<PhoneNumber> deletedPhoneNumber2 = phoneNumberRepository.findById(phoneNumber2.getId());
        assertFalse(deletedPhoneNumber2.isPresent());
    }

    @Test
    public void testEditContact() {
        // Prepare test data
        String contactId = "f4ad43b3-6416-4c59-92b7-e4de7ac02ba6";

        EmailDto email1 = new EmailDto();
        email1.setEmailAddress("john.doe@example.com");
        EmailDto email2 = new EmailDto();
        email2.setEmailAddress("johndoe@gmail.com");

        PhoneNumberDto phoneNumber1 = new PhoneNumberDto();
        phoneNumber1.setPhone("+1234567890");

        PhoneNumberDto phoneNumber2 = new PhoneNumberDto();
        phoneNumber2.setPhone("+9876543210");

        ContactDto contactDto = new ContactDto();
        contactDto.setName("John Doe");
        contactDto.setEmails(Arrays.asList(email1, email2));
        contactDto.setPhones(Arrays.asList(phoneNumber1, phoneNumber2));

        Email emailEntity = new Email();
        emailEntity.setEmailAddress("john.doe@example.com");

        Email emailEntity2 = new Email();
        emailEntity2.setEmailAddress("johndoe@gmail.com");

        PhoneNumber phoneNumberEntity1 = new PhoneNumber();
        phoneNumberEntity1.setPhone("+1111111111");

        PhoneNumber phoneNumberEntity2 = new PhoneNumber();
        phoneNumberEntity2.setPhone("+2222222222");

        Contact existingContact = new Contact();
        existingContact.setId(UUID.fromString(contactId));
        existingContact.setName("Existing Contact");
        existingContact.setEmails(new HashSet<>(Arrays.asList(
                emailEntity,
                emailEntity2
        )));

        emailEntity.setContact(existingContact);
        emailEntity2.setContact(existingContact);

        existingContact.setPhones(new HashSet<>(Arrays.asList(
                phoneNumberEntity1, phoneNumberEntity2
        )));

        phoneNumberEntity1.setContact(existingContact);
        phoneNumberEntity2.setContact(existingContact);

        Contact savedContact = contactRepository.save(existingContact);

        // Perform the edit operation
        ResponseContactDto updatedContactDto = contactService.editContact(String.valueOf(savedContact.getId()), contactDto);

        // Retrieve the updated contact from the repository
        Contact updatedContact = contactRepository.findById(savedContact.getId())
                .orElseThrow(() -> new AssertionError("Updated contact not found"));

        // Assertions
        assertNotNull(updatedContact);
        assertEquals(contactDto.getName(), updatedContact.getName());
        assertEquals(contactDto.getEmails().size(), updatedContact.getEmails().size());
        assertEquals(contactDto.getPhones().size(), updatedContact.getPhones().size());
    }

    @Test
    public void testAddContact() {
        // Prepare test data
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailAddress("john.doe@example.com");

        EmailDto emailDto2 = new EmailDto();
        emailDto2.setEmailAddress("johndoe@gmail.com");

        PhoneNumberDto phoneNumberDto = new PhoneNumberDto();
        phoneNumberDto.setPhone("+1234567890");

        PhoneNumberDto phoneNumberDto2 = new PhoneNumberDto();
        phoneNumberDto.setPhone("+9876543210");

        ContactDto contactDto = new ContactDto();
        contactDto.setName("John Doe");
        contactDto.setEmails(Arrays.asList(emailDto, emailDto2));
        contactDto.setPhones(Arrays.asList(phoneNumberDto, phoneNumberDto2));

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setLogin("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");

        User savedUser = userRepository.save(user);

        // Perform the add operation
        ResponseContactDto result = contactService.addContact(contactDto);

        // Assertions
        assertNotNull(result);
        assertEquals(contactDto.getName(), result.getName());
        assertEquals(contactDto.getEmails().size(), result.getEmails().size());
        assertEquals(contactDto.getPhones().size(), result.getPhones().size());

    }

}
