package com.contacts.phone.service;

import com.contacts.phone.dto.UserDto;
import com.contacts.phone.entity.RoleEnum;
import com.contacts.phone.entity.User;
import com.contacts.phone.exception.custom.EntityAlreadyExistsException;
import com.contacts.phone.mapper.UserMapper;
import com.contacts.phone.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import static com.contacts.phone.exception.StatusCodes.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userDto) {
        checkEmailUnique(userDto.getEmail());
        checkUsernameUnique(userDto.getLogin());

        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(RoleEnum.USER);
        return userMapper.toDto(userRepository.save(user));
    }

    private void checkEmailUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EntityAlreadyExistsException(DUPLICATE_EMAIL.name(), "This email already exists in the system");
        }
    }

    private void checkUsernameUnique(String username) {
        if (userRepository.existsByLogin(username)) {
            throw new EntityAlreadyExistsException(DUPLICATE_USERNAME.name(), "This username had been taken");
        }
    }


}
