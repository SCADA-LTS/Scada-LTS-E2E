package org.scadalts.e2e.test.core.plans.exec;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.types.TestPlan;
import org.scadalts.e2e.test.core.plans.engine.E2eSummarable;
import org.scadalts.e2e.test.core.plans.engine.TestsRunEngine;
import org.scadalts.e2e.test.core.plans.providers.TestClassesProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
class TestsExecutor implements TestsExecutable {

    private final TestClassesProvider testsProvider;
    private final TestsRunEngine testsRunEngine;

    public TestsExecutor(TestClassesProvider testsProvider, TestsRunEngine testsRunEngine) {
        this.testsProvider = testsProvider;
        this.testsRunEngine = testsRunEngine;
    }

    @Override
    public E2eSummarable execute(E2eConfig config) {
        List<Class<?>> tests = testsProvider.getClasses(config);
        if(tests.isEmpty()) {
            logger.warn("Enter at least one of the parameters to run the tests: {} \n" +
                    " The following parameters have been introduced: {}", Arrays.toString(TestPlan.values()), config);
            return E2eSummarable.empty();
        }
        _printClassesTestToExecute(tests);
        return E2eSummarable.summary(testsRunEngine.run(tests));
    }

    private void _printClassesTestToExecute(List<Class<?>> tests) {
        String testList = tests.stream()
                .map(Class::getSimpleName)
                .collect(Collectors.joining("\n"));

        logger.info("tests to run: \n\n{}\n", testList);
    }

}
