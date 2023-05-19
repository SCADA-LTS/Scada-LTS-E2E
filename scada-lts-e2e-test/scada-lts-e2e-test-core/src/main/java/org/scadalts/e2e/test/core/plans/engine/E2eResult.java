package org.scadalts.e2e.test.core.plans.engine;

import lombok.Builder;
import lombok.Getter;
import org.junit.runner.Result;
import org.scadalts.e2e.test.core.utils.TestResultPrinter;

import java.net.URL;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static org.scadalts.e2e.common.core.measure.ValueTimeUnitToPrint.preparingToPrintMs;
import static org.scadalts.e2e.test.core.utils.TestResultPrinter.failures;

@Builder
public class E2eResult {

    @Getter
    private final String testName;
    @Getter
    private final String simpleTestName;
    @Getter
    private final String sessionId;
    @Getter
    private final String description;
    @Getter
    private final URL url;
    private final Result result;

    @Getter
    private final LocalDateTime startDateTime;

    @Getter
    private final LocalDateTime endDateTime;

    public int getRunCount() {
        return result.getRunCount();
    }

    public int getFailureCount() {
        return result.getFailureCount();
    }

    public long getRunTime() {
        return result.getRunTime();
    }

    public long getExecuteTime() {
        if(endDateTime == null || startDateTime == null)
            return getRunTime();
        ZoneId zoneId = ZoneId.systemDefault().getRules().getOffset(Instant.now());
        ZoneOffset zoneOffset = ZoneOffset.of(zoneId.getId());
        Instant endInst = endDateTime.toInstant(zoneOffset);
        Instant startInst = startDateTime.toInstant(zoneOffset);
        return endInst.toEpochMilli() - startInst.toEpochMilli();
    }

    public List<E2eFailure> getFailures() {
        return result.getFailures().stream()
                .map(a -> new E2eFailure(a, sessionId, getStartDateTime()))
                .collect(Collectors.toList());
    }

    public Set<String> getFailTestNames() {
        return getFailures().stream()
                .map(E2eFailure::getDescription)
                .filter(a -> Objects.nonNull(a) && Objects.nonNull(a.getTestClass()))
                .map(a -> a.getTestClass().getSimpleName() + "." + a.getMethodName())
                .collect(Collectors.toSet());
    }

    public int getIgnoreCount() {
        return result.getIgnoreCount();
    }

    public boolean wasSuccessful() {
        return result.wasSuccessful();
    }

    public String getUrl() {
        return url.toString();
    }

    public String toString() {
        return MessageFormat.format("\n\n{0}\n{1}\n{2}\n{3}\n\n", TestResultPrinter.DECORATION_MAIN,
                failures(getFailures()), _measure(), TestResultPrinter.DECORATION_MAIN);
    }

    private String _measure() {
        return MessageFormat.format("\n{0} - run: {1}, failed: {2}, ignored: {3}\n\nruntime: {4}\n",
                getSimpleTestName(), getRunCount(), getFailureCount(), getIgnoreCount(),
                preparingToPrintMs(getRunTime()));
    }
}
