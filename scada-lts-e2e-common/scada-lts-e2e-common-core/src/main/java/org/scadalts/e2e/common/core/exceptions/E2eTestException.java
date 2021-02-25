package org.scadalts.e2e.common.core.exceptions;

public class E2eTestException extends RuntimeException {

    public E2eTestException(String message) {
        super(message);
    }

    public E2eTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
