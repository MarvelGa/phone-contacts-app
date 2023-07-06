package com.contacts.phone.exception.custom;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
