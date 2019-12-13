package org.scadalts.e2e.tests.plan;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.util.List;

import static org.scadalts.e2e.tests.plan.TestResultPrinter.printResult;


enum ScadaTestRunner {

    INSTANCE;

    private final JUnitCore jUnitCore = new JUnitCore();

    public void runAndPrint(List<Class<?>> tests) {
        tests.forEach(this::runAndPrint);
    }

    public void runAndPrint(Class<?> testClass) {
        Result result = jUnitCore.run(testClass);
        printResult(result, testClass.getName());
    }
}
