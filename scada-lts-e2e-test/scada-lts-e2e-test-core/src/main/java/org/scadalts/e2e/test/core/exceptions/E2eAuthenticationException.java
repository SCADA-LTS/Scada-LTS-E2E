package org.scadalts.e2e.test.core.exceptions;

import java.text.MessageFormat;

public class E2eAuthenticationException extends E2eTestException {

    public E2eAuthenticationException() {
    }

    public E2eAuthenticationException(String user) {
        super(MessageFormat.format("user: {}", user));
    }

    public E2eAuthenticationException(String user, Throwable cause) {
        super(MessageFormat.format("user: {}", user), cause);
    }

    public E2eAuthenticationException(Throwable cause) {
        super(cause);
    }

    public E2eAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
