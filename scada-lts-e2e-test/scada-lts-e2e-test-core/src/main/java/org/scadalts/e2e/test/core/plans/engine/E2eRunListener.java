package org.scadalts.e2e.test.core.plans.engine;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.test.core.utils.TestResultPrinter;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.scadalts.e2e.common.core.measure.ValueTimeUnitToPrint.preparingToPrintNano;

@Log4j2
class E2eRunListener extends RunListener {

    private final Class<?> test;
    private final String decorationMain = TestResultPrinter.DECORATION_MAIN;
    private final String decoration = TestResultPrinter.DECORATION;
    private long snapshot = 0;
    private final int testUuid = new Random().nextInt(999999);

    E2eRunListener(Class<?> test) {
        this.test = test;
    }

    @Override
    public void testRunStarted(Description description) {
        logger.info("\n{}\n\n[{}] testRunStarted: {}\n", decorationMain, testUuid, test.getName());
    }

    @Override
    public void testRunFinished(Result result) {
        logger.info("\n{}\n\n[{}] testRunFinished \n{}\n{}\n", decoration, testUuid, decorationMain, E2eResult.builder()
                .result(result)
                .sessionId(E2eConfiguration.sessionId)
                .simpleTestName(test.getName())
                .build());
    }

    @Override
    public void testStarted(Description description) {
        logger.info("\n{}\n\n[{}] testStarted: {}\n", decoration, testUuid, description.toString());
        snapshot = System.nanoTime();
    }

    @Override
    public void testFinished(Description description) {
        logger.info("\n\n[{}] testFinished: {} \n\ntime: {}\n{}\n", testUuid, description.toString(),
                preparingToPrintNano(System.nanoTime() - snapshot), decoration);
    }

    @Override
    public void testFailure(Failure failure) {
        logger.info("\n\n[{}] testFailure: {}", testUuid, failure.toString());
        logger.warn("\n{}: {}\n{}", failure.getException().getClass().getName(), MessageSecurityUtils.safe(failure.getException().getMessage()),
                Stream.of(failure.getException().getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.joining("\n\t")));

    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        logger.info("\n\n[{}] testAssumptionFailure: {}", testUuid, failure.toString());
        logger.warn("\n{}: {}\n{}", failure.getException().getClass().getName(), MessageSecurityUtils.safe(failure.getException().getMessage()),
                Stream.of(failure.getException().getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.joining("\n\t")));
    }

    @Override
    public void testIgnored(Description description) {
        logger.info("\n\n[{}] testIgnored: {}\n{}\n", testUuid, description.toString(), decoration);
    }
}
