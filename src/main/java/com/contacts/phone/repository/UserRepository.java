package com.contacts.phone.repository;

import com.contacts.phone.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    boolean existsByUserName(String username);

    boolean existsByEmail(String email);

    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserById(UUID uuid);

}
