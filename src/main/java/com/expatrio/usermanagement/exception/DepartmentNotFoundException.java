package com.expatrio.usermanagement.exception;

public class DepartmentNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Department not found with id: ";

    public DepartmentNotFoundException() {
    }

    public DepartmentNotFoundException(Long userId) {
        super(MESSAGE + userId + " !");
    }


    public DepartmentNotFoundException(String message) {
        super(message);
    }

    public DepartmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentNotFoundException(Throwable cause) {
        super(cause);
    }

    public DepartmentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
