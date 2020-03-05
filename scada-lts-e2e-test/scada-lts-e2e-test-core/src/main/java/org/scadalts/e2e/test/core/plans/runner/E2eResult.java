package org.scadalts.e2e.test.core.plans.runner;

import lombok.Builder;
import lombok.Getter;
import org.junit.runner.Result;

import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.scadalts.e2e.common.measure.ValueTimeUnitToPrint.preparingToPrintMs;

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
                .filter(a -> Objects.nonNull(a) && Objects.nonNull(a.getTestClass()))
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

    @Override
    public String toString() {
        return MessageFormat.format("\n{0} - run: {1}, failed: {2}, ignored: {3}\n\nruntime: {4}\n",
                getSimpleTestName(), getRunCount(), getFailureCount(), getIgnoreCount(),
                preparingToPrintMs(getRunTime()));
    }
}
