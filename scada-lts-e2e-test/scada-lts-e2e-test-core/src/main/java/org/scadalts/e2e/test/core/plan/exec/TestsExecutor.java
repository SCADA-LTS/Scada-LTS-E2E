package org.scadalts.e2e.test.core.plan.exec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.types.TestPlan;
import org.scadalts.e2e.test.core.plan.provider.TestClassesProvider;
import org.scadalts.e2e.test.core.plan.runner.TestsRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class TestsExecutor implements TestsExecutable {

    private final TestClassesProvider testsProvider;
    private final TestsRunnable testRunner;
    private static final Logger logger = LogManager.getLogger(TestsExecutor.class);

    public TestsExecutor(TestClassesProvider testsProvider, TestsRunnable testRunner) {
        this.testsProvider = testsProvider;
        this.testRunner = testRunner;
    }

    @Override
    public boolean execute(E2eConfig config) {
        List<Class<?>> tests = testsProvider.getClasses(config);
        if(tests.isEmpty()) {
            logger.warn("Enter at least one of the parameters to run the tests: {} \n" +
                    " The following parameters have been introduced: {}", Arrays.toString(TestPlan.values()), config);
            return true;
        }
        String testList = tests.stream()
                .map(Class::getSimpleName)
                .collect(Collectors.joining("\n"));

        logger.info("tests to run: \n\n{}\n", testList);

        List<TestResult> results = testRunner.runs(tests);
        return results.stream().allMatch(a -> a.getResult() != null
                && a.getResult().wasSuccessful());
    }

}
