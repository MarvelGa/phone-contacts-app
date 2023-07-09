package com.contacts.phone.repository;

import com.contacts.phone.entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface ContactRepository extends CrudRepository<Contact, UUID> {
  List<Contact> findAllByUserId(UUID userId);
}
