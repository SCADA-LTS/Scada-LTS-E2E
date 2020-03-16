package org.scadalts.e2e.common.exceptions;

import java.text.MessageFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationTooHighLoadException extends E2eTestException {

    private static final String APPLICATION_TOO_HIGH_LOAD = "Application too high load";

    public ApplicationTooHighLoadException() {
        super(APPLICATION_TOO_HIGH_LOAD);
    }

    public ApplicationTooHighLoadException(Throwable cause) {
        super(MessageFormat.format("\n{0} {1}: \n{2}\n{3}", cause.getClass().getName(), APPLICATION_TOO_HIGH_LOAD,
                cause.getMessage(), Stream.of(cause.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.joining("\n\t"))));
    }

}

