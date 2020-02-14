package org.scadalts.e2e.test.core.plan.runner;

import lombok.Getter;
import org.junit.runner.notification.Failure;

public class E2eFailure {

    private final Failure failure;

    @Getter
    private final String sessionId;

    public E2eFailure(Failure failure, String sessionId) {
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
}
