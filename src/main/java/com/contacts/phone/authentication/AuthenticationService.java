package com.contacts.phone.authentication;

import com.contacts.phone.entity.User;
import com.contacts.phone.exception.custom.EntityNotFoundException;
import com.contacts.phone.jwt.JwtService;
import com.contacts.phone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.contacts.phone.exception.StatusCodes.INVALID_PASSWORD;
import static com.contacts.phone.exception.StatusCodes.IRRELEVANT_USERNAME;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = userRepository.findUserByUserName(request.getUserName())
                .orElseThrow(() -> new EntityNotFoundException(IRRELEVANT_USERNAME.name(), "This user does not exist"));

        if (!(passwordEncoder.matches(request.getPassword(), user.getPassword()))) {
            throw new EntityNotFoundException(INVALID_PASSWORD.name(), "Wrong password. Please try again");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}

