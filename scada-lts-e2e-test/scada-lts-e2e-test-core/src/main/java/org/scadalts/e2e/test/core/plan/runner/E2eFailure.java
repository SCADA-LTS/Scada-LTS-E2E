package org.scadalts.e2e.test.core.plan.runner;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.junit.runner.notification.Failure;
import org.scadalts.e2e.common.utils.FileUtil;

import java.io.File;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class E2eFailure {

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
        return _getPathFromMessage(getMessage(),"(?<=source:)(.|\\n)*?.html");
    }

    public Optional<File> getScreenshotPng() {
        return _getPathFromMessage(getMessage(),"(?<=Screenshot:)(.|\\n)*?.png");
    }

    private static Optional<File> _getPathFromMessage(String message, String regex) {
        logger.info("message: {}", message);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if(matcher.find()) {
            String group = matcher.group().replace("http:", "").trim();
            File file = FileUtil.getFileFromFileSystem(group);
            return Optional.of(file);
        }
        return Optional.empty();
    }
}
