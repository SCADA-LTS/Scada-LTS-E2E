package org.scadalts.e2e.common.core.exceptions;

import org.apache.commons.lang3.StringUtils;

public class InitializeTestException extends E2eTestException {

    private static final String CONFIGURE_TEST_FAILED = "Test initialization failed";

    public InitializeTestException() {
        super(CONFIGURE_TEST_FAILED + ".");
    }

    public InitializeTestException(Throwable cause) {
        super(CONFIGURE_TEST_FAILED + (StringUtils.isEmpty(cause.getMessage()) ? "." : (" : " + cause.getMessage())), cause);
    }
}
