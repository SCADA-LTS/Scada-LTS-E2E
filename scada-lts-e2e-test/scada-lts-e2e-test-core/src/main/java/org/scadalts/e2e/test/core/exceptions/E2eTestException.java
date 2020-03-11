package org.scadalts.e2e.test.core.exceptions;

public class E2eTestException extends RuntimeException {

    public E2eTestException() {
    }

    public E2eTestException(String message) {
        super(message);
    }

    public E2eTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public E2eTestException(Throwable cause) {
        super(cause);
    }

    public E2eTestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
