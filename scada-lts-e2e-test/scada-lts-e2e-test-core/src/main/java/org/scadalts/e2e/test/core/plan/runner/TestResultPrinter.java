package org.scadalts.e2e.test.core.plan.runner;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.scadalts.e2e.test.core.plan.exec.TestResult;

import java.text.MessageFormat;

import static org.scadalts.e2e.common.measure.ValueTimeUnitToPrint.preparingToPrintMs;

@Log4j2
class TestResultPrinter {

    private static final String deko = "___________________________________________________";
    private static final String deko2 = "---------------------------------------------------";

    public static void print(TestResult testResult) {
        Result result = testResult.getResult();
        String testName = testResult.getTestName();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(_result(testName, result));

        if (!result.wasSuccessful()) {
            stringBuilder.append(_failures(result));
        }

        logger.info("\n\n{}\n{}\n{}\n\n", deko, stringBuilder.toString(), deko);
    }

    private static String _result(String testName, Result result) {
        return MessageFormat.format("\n{0} - run: {1}, failed: {2}, ignored: {3}\n\nruntime: {4}\n",
                testName, result.getRunCount(), result.getFailureCount(), result.getIgnoreCount(),
                preparingToPrintMs(result.getRunTime()));
    }

    private static String _failures(Result result) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Failure failure: result.getFailures()) {
            String failResult = MessageFormat.format("\n\n{0}\n\n{1}\n\n{2}\n\n", deko2, failure.toString(), deko2);
            stringBuilder.append(failResult);
        }
        return stringBuilder.toString();
    }
}
