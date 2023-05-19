package org.scadalts.e2e.test.core.plans.engine;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.measure.ValueTimeUnitToPrint;
import org.scadalts.e2e.test.core.utils.TestResultPrinter;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
    public long getExecuteTime() {
        return results.values().stream().flatMap(Collection::stream).mapToLong(E2eResult::getExecuteTime).sum();
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
    public List<E2eSummaryUnit> getResults() {
        Map<String, TestStatus> statuses = getTestStatuses();
        Map<String, String> executionTimes = getTimeFormatted(getTestExecuteTime(results));
        Map<String, String> runtimes = getTimeFormatted(getTestRuntime(results));
        Map<String, LocalDateTime> startTimes = getStartTimes(results);
        Map<String, LocalDateTime> endTimes = getEndTimes(results);
        List<String> testNames = results.entrySet().stream()
                .sorted(Comparator.comparing(a -> _reduceStartTime(a.getValue())))
                .map(a -> a.getKey().getSimpleName())
                .collect(Collectors.toList());
        AtomicInteger count = new AtomicInteger();
        return testNames.stream()
                .map(a ->
                        new E2eSummaryUnit(count.incrementAndGet() + ". " + a,
                                executionTimes.get(a), runtimes.get(a), startTimes.get(a),
                                endTimes.get(a), statuses.get(a)))
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

    private LocalDateTime _reduceStartTime(List<E2eResult> list) {
        if(list.isEmpty())
            return LocalDateTime.MIN;
        return list.get(0).getStartDateTime();
    }

    private LocalDateTime _reduceEndTime(List<E2eResult> list) {
        if(list.isEmpty())
            return LocalDateTime.MIN;
        if(list.size() == 1)
            return list.get(0).getEndDateTime();
        return list.get(1).getEndDateTime();
    }


    private static int _calcErrors(List<E2eResult> list) {
        return (int)list.stream().filter(a -> !a.wasSuccessful()).count();
    }

    private static double _calcRuntime(Map.Entry<Class<?>, List<E2eResult>> a) {
        return a.getValue().stream().mapToLong(E2eResult::getRunTime).sum();
    }

    private static String key(List<E2eFailure> failures) {
        return failures.stream().map(E2eFailure::getMessage).sorted().collect(Collectors.joining(";"));
    }

    private static Map<String, String> getTimeFormatted(Map<String, Double> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                a -> ValueTimeUnitToPrint.preparingToPrintMs(a.getValue()),
                                (b, c) -> b,
                                LinkedHashMap::new)
                );
    }


    private Map<String, LocalDateTime> getStartTimes(Map<Class<?>, List<E2eResult>> results) {
        return results.entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getKey().getSimpleName()))
                .collect(Collectors
                        .toMap(a -> a.getKey().getSimpleName(), a -> _reduceStartTime(a.getValue()),
                                (b, c) -> b.isAfter(c) ? c : b, LinkedHashMap::new)

                );
    }


    private Map<String, LocalDateTime> getEndTimes(Map<Class<?>, List<E2eResult>> results) {
        return results.entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getKey().getSimpleName()))
                .collect(Collectors
                        .toMap(a -> a.getKey().getSimpleName(),
                                a -> _reduceEndTime(a.getValue()), (b, c) -> b.isAfter(c) ? c : b,
                                LinkedHashMap::new)

                );
    }

    private static Map<String, Double> getTestExecuteTime(Map<Class<?>, List<E2eResult>> results) {
        return results.entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getKey().getSimpleName()))
                .collect(Collectors
                        .toMap(a -> a.getKey().getSimpleName(),
                                E2eSummary::_calcExecuteTime, (b, c) -> (b + c)/2.0,
                                LinkedHashMap::new)

                );
    }

    private static Map<String, Double> getTestRuntime(Map<Class<?>, List<E2eResult>> results) {
        return results.entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getKey().getSimpleName()))
                .collect(Collectors
                        .toMap(a -> a.getKey().getSimpleName(),
                                E2eSummary::_calcRuntime, (b, c) -> (b + c)/2.0,
                                LinkedHashMap::new)

                );
    }

    private static double _calcExecuteTime(Map.Entry<Class<?>, List<E2eResult>> a) {
        ZoneId zoneId = ZoneId.systemDefault().getRules().getOffset(Instant.now());
        ZoneOffset zoneOffset = ZoneOffset.of(zoneId.getId());
        double sum = 0;
        for(E2eResult result: a.getValue()) {
            sum += calc(zoneOffset, result.getEndDateTime(), result.getStartDateTime());
        }
        return sum;
    }

    private static double calc(ZoneOffset zoneOffset, LocalDateTime end, LocalDateTime start) {
        Instant endInst = end.toInstant(zoneOffset);
        Instant startInst = start.toInstant(zoneOffset);
        return endInst.toEpochMilli() - (double) startInst.toEpochMilli();
    }
}
