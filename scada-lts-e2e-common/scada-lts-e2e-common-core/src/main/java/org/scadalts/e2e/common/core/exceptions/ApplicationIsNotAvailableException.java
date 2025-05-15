package org.scadalts.e2e.common.core.exceptions;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public class ApplicationIsNotAvailableException extends E2eTestException {

    private static final String APPLICATION_IS_NOT_AVAILABLE = "Application from url: {0} is not available. {1}";

    public ApplicationIsNotAvailableException() {
        super(APPLICATION_IS_NOT_AVAILABLE);
    }

    public ApplicationIsNotAvailableException(String url, Throwable cause) {
        super(MessageFormat.format(APPLICATION_IS_NOT_AVAILABLE, url, getMessage(cause.getMessage())), cause);
    }

    public ApplicationIsNotAvailableException(String url, String msg) {
        super(MessageFormat.format(APPLICATION_IS_NOT_AVAILABLE, url, getMessage(msg)));
    }

    private static String getMessage(String message) {
        if(StringUtils.isEmpty(message)) {
            return "";
        }
        String msg = "";
        if(message.contains("net::ERR_CONNECTION_REFUSED")) {
            msg = "Problem with connecting to the application. ";
        }

        return  msg + " Cause: " + message;
    }
}
