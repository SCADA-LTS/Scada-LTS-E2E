package org.scadalts.e2e.test.core.plans.engine;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.test.core.utils.TestResultPrinter;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.scadalts.e2e.common.measure.ValueTimeUnitToPrint.preparingToPrintMs;
import static org.scadalts.e2e.test.core.utils.TestResultPrinter.failures;

@Log4j2
public class E2eSummary implements E2eSummarable {

    private final Map<Class<?>, List<E2eResult>> results;

    public E2eSummary(Map<Class<?>, List<E2eResult>> results) {
        this.results = results;
    }

    @Override
    public int getRunCount() {
        return results.values().stream().map(a -> a.get(0)).mapToInt(E2eResult::getRunCount).sum();
    }

    @Override
    public int getFailureCount() {
        return results.values().stream().map(a -> a.get(0)).mapToInt(E2eResult::getFailureCount).sum();
    }

    @Override
    public long getRunTime() {
        return results.values().stream().map(a -> a.get(0)).mapToLong(E2eResult::getRunTime).sum();
    }

    @Override
    public int getIgnoreCount() {
        return results.values().stream().map(a -> a.get(0)).mapToInt(E2eResult::getIgnoreCount).sum();
    }

    @Override
    public boolean wasSuccessful() {
        return results.values().stream().map(a -> a.get(0)).allMatch(E2eResult::wasSuccessful);
    }

    @Override
    public List<E2eFailure> getFailures() {
        return results.values().stream().map(a -> a.get(0)).flatMap(a -> a.getFailures().stream()).collect(Collectors.toList());
    }

    @Override
    public Set<String> getFailTestNames() {
        return results.values().stream().map(a -> a.get(0)).flatMap(a -> a.getFailTestNames().stream()).collect(Collectors.toSet());
    }

    @Override
    public Map<String, TestStatus> getTestStatuses() {
        return results.entrySet()
                .stream()
                .collect(Collectors
                        .toMap(a -> a.getKey().getSimpleName(),
                                a -> _reduce(a.getValue()),
                                        (b, c) -> c == TestStatus.ERROR || b == TestStatus.ERROR ? TestStatus.ERROR :
                                                c == TestStatus.NON_DETERMINISTIC_ERROR || b == TestStatus.NON_DETERMINISTIC_ERROR ? TestStatus.NON_DETERMINISTIC_ERROR : TestStatus.OK)

        );
    }
/*
    @Override
    public Map<String, String> getTestDescriptions() {
        return results.keySet()
                .stream()
                .collect(Collectors
                        .toMap(Class::getSimpleName,
                                a -> {
                                    try {
                                        return a.newInstance().getDescription();
                                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                                        logger.warn(e.getMessage(), e);
                                    }
                                    return "...";
                                },
                                (b, c) -> c)

                );
    }*/

    @Override
    public Map<String, String> getStatusesLegend() {
        Map<String, String> result = new HashMap<>();
        for(TestStatus status: TestStatus.values()) {
            result.put(status.toString(), status.getDescription());
        }
        return result;
    }

    @Override
    public String getUrl() {
        return results.values().stream().map(a -> a.get(0)).map(E2eResult::getUrl).findFirst().orElse("");
    }

    @Override
    public String toString() {
        return MessageFormat.format("\n\n{0}\n{1}\n{2}\n{3}\n{4}\n{5}\n\n", TestResultPrinter.DECORATION_MAIN,
                TestResultPrinter.DECORATION_MAIN, failures(getFailures()), _measure(), TestResultPrinter.DECORATION_MAIN,
                TestResultPrinter.DECORATION_MAIN);
    }

    private String _measure() {
        return MessageFormat.format("\n{0} - run: {1}, failed: {2}, ignored: {3}\n\nruntime: {4}\n",
                "Summary", getRunCount(), getFailureCount(), getIgnoreCount(),
                preparingToPrintMs(getRunTime()));
    }

    private static TestStatus _reduce(List<E2eResult> list) {
        if(list.isEmpty())
            return TestStatus.NONE;
        if(list.size() > 1) {
            if(list.get(1).wasSuccessful())
                return TestStatus.NON_DETERMINISTIC_ERROR;
            return TestStatus.ERROR;
        }
        return list.get(0).wasSuccessful() ? TestStatus.OK : TestStatus.ERROR;
    }

}
