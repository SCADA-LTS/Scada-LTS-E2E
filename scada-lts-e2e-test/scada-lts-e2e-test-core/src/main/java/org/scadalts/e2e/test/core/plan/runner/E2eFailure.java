package org.scadalts.e2e.test.core.plan.runner;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.util.Optional;

import static org.scadalts.e2e.test.core.utils.RegexUtil.getFilesFromMessage;

@Log4j2
public class E2eFailure {

    private final static String HTML_REGEX = "(?<=source:)(.|\\n)*?.html";
    private final static String PNG_REGEX = "(?<=Screenshot:)(.|\\n)*?.png";

    private final Failure failure;

    @Getter
    private final String sessionId;

    E2eFailure(Failure failure, String sessionId) {
        this.failure = failure;
        this.sessionId = sessionId;
    }

    public String getTestHeader() {
        return failure.getTestHeader();
    }

    public E2eDescription getDescription() {
        return new E2eDescription(failure.getDescription());
    }

    public Throwable getException() {
        return failure.getException();
    }

    public String getTrace() {
        return failure.getTrace();
    }

    public String getMessage() {
        return failure.getMessage();
    }

    @Override
    public String toString() {
        return failure.toString();
    }

    public Optional<File> getSourcePageHtml() {
        return getFilesFromMessage(getMessage(),HTML_REGEX);
    }

    public Optional<File> getScreenshotPng() {
        return getFilesFromMessage(getMessage(),PNG_REGEX);
    }
}
