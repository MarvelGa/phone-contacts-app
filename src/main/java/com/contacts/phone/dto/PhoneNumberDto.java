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
public class PhoneNumberDto {

    @Schema(description = "PhoneNumber", required = true)
    @Pattern(regexp = "\\+\\d{1,3}-\\d{3}-\\d{3}-\\d{4}", message = "Invalid phone number format. Expected format: +xxx-xxx-xxx-xxxx")
    @NotBlank( message = "PhoneNumber is required field")
    private String phone;
}
