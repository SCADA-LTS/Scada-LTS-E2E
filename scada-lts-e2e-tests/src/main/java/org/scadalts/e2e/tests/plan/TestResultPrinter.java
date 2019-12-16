package org.scadalts.e2e.tests.plan;

import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TestResultPrinter {

    private static Logger logger = LoggerFactory.getLogger(TestResultPrinter.class);

    public static void printResult(Result result, String testName) {
        logger.info("______________________________________________________________\n{} - run: {}, failed: {}, ignored: {}",
                testName, result.getRunCount(), result.getFailureCount(), result.getIgnoreCount());
        if (!result.wasSuccessful()) {
            for (Failure failure: result.getFailures()) {
                logger.info("______________________________________________________________\n{}", failure.toString());
            }
        }
    }
}
