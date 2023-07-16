package com.expatrio.usermanagement.exception;

/**
 * The type User not found exception.
 */
public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User not found with id: ";

    /**
     * Instantiates a new User not found exception.
     */
    public UserNotFoundException() {
    }

    /**
     * Instantiates a new User not found exception.
     *
     * @param userId the user id
     */
    public UserNotFoundException(Long userId) {
        super(MESSAGE + userId + " !");
    }

    /**
     * Instantiates a new User not found exception.
     *
     * @param message the message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new User not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new User not found exception.
     *
     * @param cause the cause
     */
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new User not found exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
