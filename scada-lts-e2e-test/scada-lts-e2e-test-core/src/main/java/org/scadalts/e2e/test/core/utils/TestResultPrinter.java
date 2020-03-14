package org.scadalts.e2e.test.core.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.test.core.plans.engine.E2eFailure;
import org.scadalts.e2e.test.core.plans.engine.E2eResult;
import org.scadalts.e2e.test.core.plans.engine.E2eSummarable;

import java.text.MessageFormat;

@Log4j2
public class TestResultPrinter {

    private static final String deko = "___________________________________________________";
    private static final String deko2 = "---------------------------------------------------";

    public static void print(E2eResult testResult) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(testResult.toString());

        if (!testResult.wasSuccessful()) {
            stringBuilder.append(failures(testResult));
        }

        logger.info("\n\n{}\n{}\n{}\n\n", deko, stringBuilder.toString(), deko);
    }

    public static void print(E2eSummarable summary) {
        logger.info("\n\n{}\n{}\n{}\n\n", deko, summary.toString(), deko);
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