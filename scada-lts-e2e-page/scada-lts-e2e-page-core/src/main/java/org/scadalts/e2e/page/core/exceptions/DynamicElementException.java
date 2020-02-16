package org.scadalts.e2e.page.core.exceptions;

public class DynamicElementException extends Exception {

    public DynamicElementException() {
    }

    public DynamicElementException(String message) {
        super(message);
    }

    public DynamicElementException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicElementException(Throwable cause) {
        super(cause);
    }

    public DynamicElementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
