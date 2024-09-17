package com.cercli.shared.exception;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class EmployeeCreationFailedException extends RuntimeException {

    public EmployeeCreationFailedException(final String message) {
        super(message);
    }
}
