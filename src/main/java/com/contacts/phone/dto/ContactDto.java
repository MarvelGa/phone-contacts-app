package com.contacts.phone.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
    private UUID id;

    @NotBlank(message = "name is required field")
    @Length(min = 2, max = 30, message = "name should contain from 2 to 30 characters")
    @Schema(description = "name", required = true)
    private String name;

    private List<EmailDto> emails;

    private List<PhoneNumberDto> phones;
}
