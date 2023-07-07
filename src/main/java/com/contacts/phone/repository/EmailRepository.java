package com.contacts.phone.repository;

import com.contacts.phone.entity.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Transactional
@Repository
public interface EmailRepository extends CrudRepository<Email, UUID> {
}
