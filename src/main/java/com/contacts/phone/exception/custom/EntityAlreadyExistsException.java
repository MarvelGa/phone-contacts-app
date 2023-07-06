package com.contacts.phone.exception.custom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityAlreadyExistsException extends BaseException {

    public EntityAlreadyExistsException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
