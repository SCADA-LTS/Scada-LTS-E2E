package org.scadalts.e2e.test.core.plan.runner;

import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;

import static org.scadalts.e2e.common.measure.ValueTimeUnitToPrint.preparingToPrintMs;

@Log4j2
public class TestResultPrinter {

    private static final String deko = "___________________________________________________";
    private static final String deko2 = "---------------------------------------------------";

    public static void print(E2eResult testResult) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(result(testResult));

        if (!testResult.wasSuccessful()) {
            stringBuilder.append(failures(testResult));
        }

        logger.info("\n\n{}\n{}\n{}\n\n", deko, stringBuilder.toString(), deko);
    }

    public static String result(E2eResult result) {
        return MessageFormat.format("\n{0} - run: {1}, failed: {2}, ignored: {3}\n\nruntime: {4}\n",
                result.getSimpleTestName(), result.getRunCount(), result.getFailureCount(), result.getIgnoreCount(),
                preparingToPrintMs(result.getRunTime()));
    }

    public static String failures(E2eResult result) {
        StringBuilder stringBuilder = new StringBuilder();
        for (E2eFailure failure: result.getFailures()) {
            String failResult = MessageFormat.format("\n\n{0}\n\n{1}\n\n{2}\n\n", deko2, failure.toString(), deko2);
            stringBuilder.append(failResult);
        }
        return stringBuilder.toString();
    }
}
