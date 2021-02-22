package org.scadalts.e2e.common.core.exceptions;

import org.apache.commons.lang3.StringUtils;

public class TestsNotInitializedException extends E2eTestException {

    private static final String CONFIGURE_TEST_FAILED = "Tests not initialized";

    public TestsNotInitializedException() {
        super(CONFIGURE_TEST_FAILED + ".");
    }

    public TestsNotInitializedException(Throwable cause) {
        super(CONFIGURE_TEST_FAILED + (StringUtils.isEmpty(cause.getMessage()) ? "." : (" : " + cause.getMessage())), cause);
    }
}
