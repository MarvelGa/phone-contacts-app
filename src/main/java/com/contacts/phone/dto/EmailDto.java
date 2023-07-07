package com.contacts.phone.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    @NotBlank(message = "Email is required field")
    @Pattern(regexp = "^[^@]+@[^@.]+\\.[^@.]+$", message = "Email address should contain @ and . symbols")
    @Schema(description = "Email", required = true)
    private String emailAddress;
}
