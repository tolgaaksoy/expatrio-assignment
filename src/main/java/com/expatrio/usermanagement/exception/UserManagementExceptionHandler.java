package com.expatrio.usermanagement.exception;

import com.expatrio.usermanagement.model.payload.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

/**
 * The type User management exception handler.
 */
@Slf4j
@RestControllerAdvice
public class UserManagementExceptionHandler {

    /**
     * Handle t user not found exception base response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the base response
     */
    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse handleTUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return BaseResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .build();
    }

    /**
     * Handle t user not found exception base response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the base response
     */
    @ExceptionHandler(value = DepartmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse handleTUserNotFoundException(DepartmentNotFoundException ex, WebRequest request) {
        return BaseResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(value = UsernameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseResponse handleUsernameAlreadyExistException(UsernameAlreadyExistException ex, WebRequest request) {
        return BaseResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .build();
    }

}
