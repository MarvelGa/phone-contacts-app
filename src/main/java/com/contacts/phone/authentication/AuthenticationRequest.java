package com.contacts.phone.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "User name cannot be blank")
    @NotNull(message = "Username fields are required")
    private String login;
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password fields are required")
    private String password;
}
