package org.scadalts.e2e.common.exceptions;

import java.text.MessageFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationIsNotAvailableException extends E2eTestException {

    private static final String APPLICATION_IS_NOT_AVAILABLE = "Application is not available";

    public ApplicationIsNotAvailableException() {
        super(APPLICATION_IS_NOT_AVAILABLE);
    }

    public ApplicationIsNotAvailableException(Throwable cause) {
        super(MessageFormat.format("\n{0} {1}: \n{2}\n{3}", cause.getClass().getName(), APPLICATION_IS_NOT_AVAILABLE,
                cause.getMessage(), Stream.of(cause.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.joining("\n\t"))));


    }
}
