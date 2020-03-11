package org.scadalts.e2e.test.core.exceptions;

public class ApplicationTooHighLoadException extends E2eTestException {
    public ApplicationTooHighLoadException() {
    }

    public ApplicationTooHighLoadException(String message) {
        super(message);
    }

    public ApplicationTooHighLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationTooHighLoadException(Throwable cause) {
        super(cause);
    }

    public ApplicationTooHighLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

