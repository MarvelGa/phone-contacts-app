package com.contacts.phone.controller;

import com.contacts.phone.dto.ContactDto;
import com.contacts.phone.dto.ResponseContactDto;
import com.contacts.phone.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ResponseContactDto> addContact(@RequestBody ContactDto contactDto) {
        ResponseContactDto savedContact = contactService.addContact(contactDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<ResponseContactDto> editContact(
            @PathVariable String contactId,
            @RequestBody ContactDto contactDto
    ) {
        ResponseContactDto editedContact = contactService.editContact(contactId, contactDto);
        return ResponseEntity.ok(editedContact);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable String contactId) {
        contactService.deleteContact(contactId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseContactDto>> getAllContacts() {
        List<ResponseContactDto> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }
}
