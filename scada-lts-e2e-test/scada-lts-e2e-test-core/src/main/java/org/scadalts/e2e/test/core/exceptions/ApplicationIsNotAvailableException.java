package org.scadalts.e2e.test.core.exceptions;

public class ApplicationIsNotAvailableException extends E2eTestException {

    public ApplicationIsNotAvailableException() {
    }

    public ApplicationIsNotAvailableException(String message) {
        super(message);
    }

    public ApplicationIsNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationIsNotAvailableException(Throwable cause) {
        super(cause);
    }

    public ApplicationIsNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
