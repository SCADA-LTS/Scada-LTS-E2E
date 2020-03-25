package org.scadalts.e2e.common.exceptions;

import java.text.MessageFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigureTestException extends E2eTestException {

    private static final String CONFIGURE_TEST_FAILED = "Configure test failed";

    public ConfigureTestException(Throwable cause) {
        super(MessageFormat.format("\n{0} {1}: \n{2}\n{3}", cause.getClass().getName(),
                CONFIGURE_TEST_FAILED, cause.getMessage(), Stream.of(cause.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.joining("\n\t"))));


    }
}
