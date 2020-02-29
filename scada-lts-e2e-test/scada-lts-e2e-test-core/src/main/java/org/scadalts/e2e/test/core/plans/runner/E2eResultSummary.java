package org.scadalts.e2e.test.core.plans.runner;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.scadalts.e2e.common.measure.ValueTimeUnitToPrint.preparingToPrintMs;

public class E2eResultSummary implements E2eResultSummarable {

    private final List<E2eResult> results;

    public E2eResultSummary(List<E2eResult> results) {
        this.results = results;
    }

    public static E2eResultSummary empty() {
        return new E2eResultSummary(Collections.emptyList());
    }

    @Override
    public int getRunCount() {
        return results.stream().mapToInt(E2eResult::getRunCount).sum();
    }

    @Override
    public int getFailureCount() {
        return results.stream().mapToInt(E2eResult::getFailureCount).sum();
    }

    @Override
    public long getRunTime() {
        return results.stream().mapToLong(E2eResult::getRunTime).sum();
    }

    public List<E2eResult> getResults() {
        return results;
    }

    @Override
    public int getIgnoreCount() {
        return results.stream().mapToInt(E2eResult::getIgnoreCount).sum();
    }

    @Override
    public boolean wasSuccessful() {
        return results.stream().allMatch(E2eResult::wasSuccessful);
    }

    @Override
    public List<E2eFailure> getFailures() {
        return results.stream().flatMap(a -> a.getFailures().stream()).collect(Collectors.toList());
    }

    @Override
    public Set<String> getFailTestNames() {
        return results.stream().flatMap(a -> a.getFailTestNames().stream()).collect(Collectors.toSet());
    }

    public String getUrl() {
        return results.stream().map(E2eResult::getUrl).map(URL::toString).findFirst().orElse("");
    }

    @Override
    public String toString() {
        return MessageFormat.format("\n{0} - run: {1}, failed: {2}, ignored: {3}\n\nruntime: {4}\n",
                "Summary", getRunCount(), getFailureCount(), getIgnoreCount(),
                preparingToPrintMs(getRunTime()));
    }
}
