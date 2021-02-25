package org.scadalts.e2e.common.core.exceptions;

import org.apache.commons.lang3.StringUtils;

public class ApplicationIsNotAvailableException extends E2eTestException {

    private static final String APPLICATION_IS_NOT_AVAILABLE = "Application is not available";

    public ApplicationIsNotAvailableException() {
        super(APPLICATION_IS_NOT_AVAILABLE);
    }

    public ApplicationIsNotAvailableException(Throwable cause) {
        super(APPLICATION_IS_NOT_AVAILABLE + (!StringUtils.isEmpty(cause.getMessage()) && cause.getMessage().contains("net::ERR_CONNECTION_REFUSED") ?
                " : Problem with connecting to the application." : (StringUtils.isEmpty(cause.getMessage()) ? "." : (" : " + cause.getMessage()))), cause);
    }

    public ApplicationIsNotAvailableException(String msg) {
        super(APPLICATION_IS_NOT_AVAILABLE + ". Problem with connecting to the application" + (StringUtils.isEmpty(msg) ? "." : " : " + msg));
    }
}
