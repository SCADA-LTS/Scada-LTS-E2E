package org.scadalts.e2e.test.core.plan.runner;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.scadalts.e2e.test.core.plan.exec.TestResult;

import java.util.ArrayList;
import java.util.List;

@Log4j2
class E2eTestsRunner implements TestsRunnable {

    private final JUnitCore jUnitCore;

    E2eTestsRunner(JUnitCore jUnitCore) {
        this.jUnitCore = jUnitCore;
    }

    @Override
    public List<TestResult> runs(List<Class<?>> tests) {
        List<TestResult> results = new ArrayList<>();
        try {
            for (Class<?> test: tests) {
                RunListener scada = new E2eRunListener(test);
                TestResult result = _run(test, scada);
                results.add(result);
            }
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);
        }
        return results;
    }

    private TestResult _run(Class<?> test, RunListener runListener) {
        jUnitCore.addListener(runListener);
        Result result = jUnitCore.run(test);
        jUnitCore.removeListener(runListener);
        return new TestResult(test.getName(), result);
    }
}
