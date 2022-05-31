package org.scadalts.e2e.test.core.plans.engine;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.test.core.utils.TestResultPrinter;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.scadalts.e2e.common.core.measure.ValueTimeUnitToPrint.preparingToPrintMs;
import static org.scadalts.e2e.test.core.utils.TestResultPrinter.failures;

@Log4j2
public class E2eSummary implements E2eSummarable {

    private final Map<Class<?>, List<E2eResult>> results;

    public E2eSummary(Map<Class<?>, List<E2eResult>> results) {
        this.results = results;
    }

    @Override
    public int getRunCount() {
        return results.values().stream().flatMap(Collection::stream).mapToInt(E2eResult::getRunCount).sum();
    }

    @Override
    public int getFailureCount() {
        return results.values().stream().flatMap(Collection::stream).mapToInt(E2eResult::getFailureCount).sum();
    }

    @Override
    public long getRunTime() {
        return results.values().stream().flatMap(Collection::stream).mapToLong(E2eResult::getRunTime).sum();
    }

    @Override
    public int getIgnoreCount() {
        return results.values().stream().flatMap(Collection::stream).mapToInt(E2eResult::getIgnoreCount).sum();
    }

    @Override
    public boolean wasSuccessful() {
        return results.values().stream().flatMap(Collection::stream).allMatch(E2eResult::wasSuccessful);
    }

    @Override
    public List<E2eFailure> getFailures() {
        return results.values().stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(E2eResult::getStartDateTime))
                .flatMap(a -> a.getFailures().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFailTestNames() {
        return results.values().stream()
                .flatMap(Collection::stream)
                .flatMap(a -> a.getFailTestNames().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, TestStatus> getTestStatuses() {
        return results.entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getKey().getSimpleName()))
                .collect(Collectors.toMap(a -> a.getKey().getSimpleName(),
                                a -> _reduce(a.getValue()),
                                        (b, c) -> c == TestStatus.ERROR || b == TestStatus.ERROR ? TestStatus.ERROR :
                                                c == TestStatus.NON_DETERMINISTIC_ERROR || b == TestStatus.NON_DETERMINISTIC_ERROR ? TestStatus.NON_DETERMINISTIC_ERROR : TestStatus.OK,
                        LinkedHashMap::new)

        );
    }

    @Override
    public Map<String, LocalDateTime> getStartTimes() {
        return results.entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getValue().get(0).getStartDateTime()))
                .collect(Collectors
                        .toMap(a -> a.getKey().getSimpleName(),
                                a -> _reduceStartTime(a.getValue()), (b, c) -> b.compareTo(c) > 0 ? c : b,
                                LinkedHashMap::new)

                );
    }


    @Override
    public Map<String, LocalDateTime> getEndTimes() {
        return results.entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getValue().get(0).getStartDateTime()))
                .collect(Collectors
                        .toMap(a -> a.getKey().getSimpleName(),
                                a -> _reduceEndTime(a.getValue()), (b, c) -> b.compareTo(c) > 0 ? c : b,
                                LinkedHashMap::new)

                );
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof E2eSummary)) return false;
        E2eSummary that = (E2eSummary) o;
        return key(getFailures()).equals(key(that.getFailures()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(key(getFailures()));
    }

    private String _measure() {
        return MessageFormat.format("\n{0} - run: {1}, failed: {2}, ignored: {3}\n\nruntime: {4}\n",
                "Summary", getRunCount(), getFailureCount(), getIgnoreCount(),
                preparingToPrintMs(getRunTime()));
    }

    private static TestStatus _reduce(List<E2eResult> list) {
        if(list.isEmpty())
            return TestStatus.NONE;
        int error = _calcErrors(list);
        if(error == 0)
            return TestStatus.OK;
        if(error < list.size())
            return TestStatus.NON_DETERMINISTIC_ERROR;
        return TestStatus.ERROR;
    }

    private static LocalDateTime _reduceStartTime(List<E2eResult> list) {
        if(list.isEmpty())
            return LocalDateTime.MIN;
        return list.get(0).getStartDateTime();
    }

    private static LocalDateTime _reduceEndTime(List<E2eResult> list) {
        if(list.isEmpty())
            return LocalDateTime.MIN;
        return list.get(0).getEndDateTime();
    }


    private static int _calcErrors(List<E2eResult> list) {
        return (int)list.stream().filter(a -> !a.wasSuccessful()).count();
    }

    private static String key(List<E2eFailure> failures) {
        return failures.stream().map(E2eFailure::getMessage).sorted().collect(Collectors.joining(";"));
    }
}
