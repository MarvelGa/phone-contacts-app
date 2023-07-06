package com.contacts.phone.exception;
import com.contacts.phone.dto.RemoteResponse;
import com.contacts.phone.exception.custom.BaseException;
import com.contacts.phone.exception.custom.EntityAlreadyExistsException;
import com.contacts.phone.exception.custom.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public RemoteResponse invalidEntityException(BaseException ex) {
        log.error("The error occur with message={}", ex.getMessage());
        return RemoteResponse.create(false, ex.getErrorCode(), ex.getMessage(), null);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public RemoteResponse notFoundException(BaseException ex) {
        log.error("The error occur with message={}", ex.getMessage());
        return RemoteResponse.create(false, ex.getErrorCode(), ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RemoteResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ObjectError objectError = ex.getBindingResult().getAllErrors().stream().findFirst().get();
        log.error("The error occur with message={}", objectError.getDefaultMessage());
        return RemoteResponse.create(false, StatusCodes.INVALID_DATA.name(), objectError.getDefaultMessage(), null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public RemoteResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("The error occur with message={}", ex.getMessage());
        return RemoteResponse.create(false, StatusCodes.SQL_ERROR.name(), "SQL error", null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RemoteResponse handleAccessDeniedException(AccessDeniedException ex) {
        log.error("The error occur with message={}", ex.getMessage());
        return RemoteResponse.create(false, StatusCodes.FORBIDDEN.name(), ex.getMessage(), null);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public RemoteResponse handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        log.error("The error occur with message={}", ex.getMessage());
        return RemoteResponse.create(false, StatusCodes.UNSUPPORTED_MEDIA_TYPE.name(), ex.getMessage(), null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RemoteResponse handleHttpMessageNotReadableException(Exception ex) {
        log.error("The error occur with message={}", ex.getMessage());
        return RemoteResponse.create(false, HttpStatus.BAD_REQUEST.name(), ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public RemoteResponse genericException(Exception ex) {
        log.error("The error occur with message={}", ex.getMessage());
        return RemoteResponse.create(false, StatusCodes.GENERIC_ERROR.name(), ex.getMessage(), null);
    }

}
