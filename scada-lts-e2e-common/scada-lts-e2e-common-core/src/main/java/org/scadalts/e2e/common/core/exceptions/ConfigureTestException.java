package org.scadalts.e2e.common.core.exceptions;


import org.apache.commons.lang3.StringUtils;

public class ConfigureTestException extends E2eTestException {

    private static final String CONFIGURE_TEST_FAILED = "Configure test failed";


    public ConfigureTestException(String msg, Throwable cause) {
        super(CONFIGURE_TEST_FAILED + (StringUtils.isEmpty(msg) ? "." : (" : " + msg)) + (StringUtils.isEmpty(cause.getMessage()) ? "." : (" : " + cause.getMessage())), cause);
    }

    public ConfigureTestException(Throwable cause) {
        super(CONFIGURE_TEST_FAILED + (StringUtils.isEmpty(cause.getMessage()) ? "." : (" : " + cause.getMessage())), cause);
    }
}
