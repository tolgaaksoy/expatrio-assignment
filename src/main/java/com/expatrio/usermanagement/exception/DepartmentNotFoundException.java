package com.expatrio.usermanagement.exception;

/**
 * The type Department not found exception.
 */
public class DepartmentNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Department not found with id: ";

    /**
     * Instantiates a new Department not found exception.
     */
    public DepartmentNotFoundException() {
    }

    /**
     * Instantiates a new Department not found exception.
     *
     * @param userId the user id
     */
    public DepartmentNotFoundException(Long userId) {
        super(MESSAGE + userId + " !");
    }


    /**
     * Instantiates a new Department not found exception.
     *
     * @param message the message
     */
    public DepartmentNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Department not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public DepartmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Department not found exception.
     *
     * @param cause the cause
     */
    public DepartmentNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Department not found exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public DepartmentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
