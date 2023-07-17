package com.expatrio.usermanagement.exception;

public class UsernameAlreadyExistException extends RuntimeException {

    public UsernameAlreadyExistException(String username) {
        super("Username " + username + " already exists");
    }

    public UsernameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public UsernameAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UsernameAlreadyExistException() {
    }
}
