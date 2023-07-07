package com.contacts.phone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseContactDto {
    private String name;

    private List<String> emails;

    private List<String> phones;
}
