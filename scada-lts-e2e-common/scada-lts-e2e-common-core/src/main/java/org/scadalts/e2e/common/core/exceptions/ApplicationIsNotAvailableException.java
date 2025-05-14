package org.scadalts.e2e.common.core.exceptions;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public class ApplicationIsNotAvailableException extends E2eTestException {

    private static final String APPLICATION_IS_NOT_AVAILABLE = "Application url: {0} is not available. Problem with connecting to the application {1}";

    public ApplicationIsNotAvailableException() {
        super(APPLICATION_IS_NOT_AVAILABLE);
    }

    public ApplicationIsNotAvailableException(String url, Throwable cause) {
        super(MessageFormat.format(APPLICATION_IS_NOT_AVAILABLE, url, (!StringUtils.isEmpty(cause.getMessage()) && cause.getMessage().contains("net::ERR_CONNECTION_REFUSED") ?
                " : Problem with connecting to the application." : (StringUtils.isEmpty(cause.getMessage()) ? "." : (" : " + cause.getMessage())))), cause);
    }

    public ApplicationIsNotAvailableException(String url, String msg) {
        super(MessageFormat.format(APPLICATION_IS_NOT_AVAILABLE, url, (StringUtils.isEmpty(msg) ? "." : " : " + msg)));
    }
}
