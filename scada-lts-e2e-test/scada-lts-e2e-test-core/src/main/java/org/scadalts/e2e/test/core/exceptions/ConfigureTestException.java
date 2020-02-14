package org.scadalts.e2e.test.core.exceptions;

public class ConfigureTestException extends Exception {

    public ConfigureTestException() {
    }

    public ConfigureTestException(String message) {
        super(message);
    }

    public ConfigureTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigureTestException(Throwable cause) {
        super(cause);
    }

    public ConfigureTestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
