package org.scadalts.e2e.common.exceptions;

public class E2eTestsNotInitializedException extends E2eTestException {

    private static final String CONFIGURE_TEST_FAILED = "Tests not initialized";

    public E2eTestsNotInitializedException() {
        super(CONFIGURE_TEST_FAILED);
    }

    public E2eTestsNotInitializedException(Throwable cause) {
        super(CONFIGURE_TEST_FAILED, cause);
    }
}
