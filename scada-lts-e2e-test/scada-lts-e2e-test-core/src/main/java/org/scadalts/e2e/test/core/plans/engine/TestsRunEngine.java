package org.scadalts.e2e.test.core.plans.engine;

import org.junit.runner.JUnitCore;

import java.util.List;

public interface TestsRunEngine {
    List<E2eResult> run(List<Class<?>> tests);

    static TestsRunEngine newInstance() {
        return new TestsRunJUnitCore(new JUnitCore());
    }
}
