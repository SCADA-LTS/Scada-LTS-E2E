package org.scadalts.e2e.test.core.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.test.core.plans.engine.E2eFailure;

import java.util.List;

@Log4j2
public class TestResultPrinter {

    public static final String DECORATION_MAIN = "___________________________________________________";
    public static final String DECORATION = "---------------------------------------------------";

    public static String failures(List<E2eFailure> failures) {
        StringBuilder stringBuilder = new StringBuilder();
        for (E2eFailure failure: failures) {
            stringBuilder.append(failure.toString());
        }
        return stringBuilder.toString();
    }
}
