package org.scadalts.e2e.common.core.exceptions;

import org.apache.commons.lang3.StringUtils;

public class E2eLoginException extends E2eTestException {

    private static final String LOGIN_FAILED = "Login failed";

    public E2eLoginException() {
        super(LOGIN_FAILED + ".");
    }

    public E2eLoginException(String message) {
        super(LOGIN_FAILED + (StringUtils.isEmpty(message) ? "." : " : " + message));
    }

    public E2eLoginException(Throwable cause) {
        super(LOGIN_FAILED + (StringUtils.isEmpty(cause.getMessage()) ? "." : " : " + cause.getMessage()), cause);
    }

    public E2eLoginException(String message, Throwable cause) {
        super(LOGIN_FAILED + (StringUtils.isEmpty(message) ? "." : " : " + message), cause);
    }
}
