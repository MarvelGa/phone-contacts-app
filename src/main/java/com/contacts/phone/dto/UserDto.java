package com.contacts.phone.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@Schema(description = "User Data Transfer Object, specify role:USER to use this type of DTO")
public class UserDto {

    @Schema(description = "User id")
    private String id;

    @NotBlank(message = "Username is required field")
    @Length(min = 2, max = 30, message = "Username should contain from 2 to 30 characters")
    @Schema(description = "User name", required = true)
    private String userName;

    @NotBlank(message = "Email is required field")
    @Pattern(regexp = "^[^@]+@[^@.]+\\.[^@.]+$", message = "Email address should contain @ and . symbols")
    @Schema(description = "Email", required = true)
    private String email;

    @NotBlank( message = "Password is required field")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{7,30}$",
            message = "Your password must contain upper and lower case letters and numbers, at least 7 and maximum 30 characters." +
                    "Password cannot contains spaces")
    @Schema(description = "Password", required = true)
    private String password;

}
