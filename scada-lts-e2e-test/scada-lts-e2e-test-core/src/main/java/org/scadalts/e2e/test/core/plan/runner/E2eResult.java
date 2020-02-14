package org.scadalts.e2e.test.core.plan.runner;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.junit.runner.Result;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Builder
public class E2eResult implements E2eResultSummarable {

    @Getter
    private final String testName;
    @Getter
    private final String simpleTestName;
    @Getter
    private final String sessionId;
    @Getter
    private final URL url;
    private final Result result;


    @Override
    public int getRunCount() {
        return result.getRunCount();
    }

    @Override
    public int getFailureCount() {
        return result.getFailureCount();
    }

    @Override
    public long getRunTime() {
        return result.getRunTime();
    }

    @Override
    public List<E2eFailure> getFailures() {
        return result.getFailures().stream()
                .map(a -> new E2eFailure(a, sessionId))
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getFailTestNames() {
        return getFailures().stream()
                .map(E2eFailure::getDescription)
                .map(a -> a.getTestClass().getSimpleName() + "." + a.getMethodName())
                .collect(Collectors.toSet());
    }

    @Override
    public int getIgnoreCount() {
        return result.getIgnoreCount();
    }

    @Override
    public boolean wasSuccessful() {
        return result.wasSuccessful();
    }
}
