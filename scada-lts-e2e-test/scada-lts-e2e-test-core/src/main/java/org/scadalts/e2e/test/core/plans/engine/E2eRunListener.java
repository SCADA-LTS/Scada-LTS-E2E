package org.scadalts.e2e.test.core.plans.engine;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.test.core.utils.TestResultPrinter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.scadalts.e2e.common.measure.ValueTimeUnitToPrint.preparingToPrintNano;

@Log4j2
class E2eRunListener extends RunListener {

    private final Class<?> test;
    private final String deko = "___________________________________________________";
    private final String deko2 = "---------------------------------------------------";
    private long snapshot = 0;

    E2eRunListener(Class<?> test) {
        this.test = test;
    }

    @Override
    public void testRunStarted(Description description) {
        logger.info("\n{}\n\ntestRunStarted: {}\n", deko, test.getName());
    }

    @Override
    public void testRunFinished(Result result) {
        logger.info("\n{}\n\ntestRunFinished \n{}\n", deko2, deko);
        TestResultPrinter.print(E2eResult.builder()
                .result(result)
                .sessionId(E2eConfiguration.sessionId)
                .simpleTestName(test.getName())
                .build());
    }

    @Override
    public void testStarted(Description description) {
        logger.info("\n\ntestStarted: {}\n", description.toString());
        snapshot = System.nanoTime();
    }

    @Override
    public void testFinished(Description description) {
        logger.info("\n\ntestFinished: {} \n\ntime: {}\n{}\n", description.toString(),
                preparingToPrintNano(System.nanoTime() - snapshot), deko2);
    }

    @Override
    public void testFailure(Failure failure) {
        logger.info("\n\ntestFailure: {}", failure.toString());
        logger.warn("\n{}: {}\n{}", failure.getException().getClass().getName(), failure.getException().getMessage(),
                Stream.of(failure.getException().getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.joining("\n\t")));

    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        logger.info("\n\ntestAssumptionFailure: {}", failure.toString());
        logger.warn("\n{}: {}\n{}", failure.getException().getClass().getName(), failure.getException().getMessage(),
                Stream.of(failure.getException().getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.joining("\n\t")));
    }

    @Override
    public void testIgnored(Description description) {
        logger.info("\n\ntestIgnored: {}\n{}\n", description.toString(), deko2);
    }
}
