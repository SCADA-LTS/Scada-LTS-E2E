package org.scadalts.e2e.common.exceptions;

import java.text.MessageFormat;

public class E2eAuthenticationException extends E2eTestException {

    private static final String AUTHENTICATION_FAILED_FOR_USER_X = "Authentication failed for user: {0}";

    public E2eAuthenticationException(String user) {
        super(MessageFormat.format(AUTHENTICATION_FAILED_FOR_USER_X, user));
    }

    public E2eAuthenticationException(String user, Throwable cause) {
        super(MessageFormat.format(AUTHENTICATION_FAILED_FOR_USER_X, user), cause);
    }
}
