package com.contacts.phone.exception.custom;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private String errorCode;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String errorCode, String errorMessage) {
        this(errorMessage);
        this.errorCode = errorCode;
    }

}
