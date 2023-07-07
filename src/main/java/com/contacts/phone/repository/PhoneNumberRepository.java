package com.contacts.phone.repository;

import com.contacts.phone.entity.PhoneNumber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Transactional
@Repository
public interface PhoneNumberRepository  extends CrudRepository<PhoneNumber, UUID> {
}
