package org.scadalts.e2e.test.core.plan.runner;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.scadalts.e2e.test.core.plan.exec.TestResult;

import static org.scadalts.e2e.common.measure.ValueTimeUnitToPrint.preparingToPrintNano;

@Log4j2
class E2eRunListener extends RunListener {

    private final Class<?> test;
    private final String deko = "___________________________________________________";
    private final String deko2 = "---------------------------------------------------";
    private long snapshot = 0;

    public E2eRunListener(Class<?> test) {
        this.test = test;
    }

    @Override
    public void testRunStarted(Description description) throws Exception {
        logger.info("\n{}\n\ntestRunStarted: {}\n", deko, test.getName());
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        logger.info("\n{}\n\ntestRunFinished \n{}\n", deko2, deko);
        TestResultPrinter.print(new TestResult(test.getName(), result));
    }

    @Override
    public void testStarted(Description description) throws Exception {
        logger.info("\n\ntestStarted: {}\n", description.toString());
        snapshot = System.nanoTime();
    }

    @Override
    public void testFinished(Description description) throws Exception {
        logger.info("\n\ntestFinished: {} \n\ntime: {}\n{}\n", description.toString(),
                preparingToPrintNano(System.nanoTime() - snapshot), deko2);
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        logger.info("\n\ntestFailure: {}", failure.toString());
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        logger.info("\n\ntestAssumptionFailure: {}", failure.toString());
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        logger.info("\n\ntestIgnored: {}\n{}\n", description.toString(), deko2);
    }
}
