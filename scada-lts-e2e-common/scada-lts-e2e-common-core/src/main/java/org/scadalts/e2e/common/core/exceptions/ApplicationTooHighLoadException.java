package org.scadalts.e2e.common.core.exceptions;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public class ApplicationTooHighLoadException extends E2eTestException {

    private static final String APPLICATION_TOO_HIGH_LOAD = "Application too high load. Page loading time exceeded {1} seconds. Url: {0}";

    public ApplicationTooHighLoadException(String url, long timeoutMs) {
        super(MessageFormat.format(APPLICATION_TOO_HIGH_LOAD, url, timeoutMs/1000.0));
    }

    public ApplicationTooHighLoadException(Throwable cause, String url, long timeoutMs) {
        super(MessageFormat.format(APPLICATION_TOO_HIGH_LOAD, url, timeoutMs/1000.0) + (StringUtils.isEmpty(cause.getMessage()) ? "." : ( " : " + cause.getMessage())), cause);
    }

}

