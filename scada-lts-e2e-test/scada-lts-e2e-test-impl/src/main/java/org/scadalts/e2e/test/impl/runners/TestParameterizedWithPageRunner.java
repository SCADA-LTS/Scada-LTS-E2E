package org.scadalts.e2e.test.impl.runners;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.test.core.exceptions.E2eTestException;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

public class TestParameterizedWithPageRunner extends Parameterized {
    public TestParameterizedWithPageRunner(Class<?> klass) throws Throwable {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            TestWithPageUtil.preparingTest();
        } catch (Throwable throwable) {
            TestWithPageUtil.close();
            throw new E2eTestException(throwable.getMessage(), throwable);
        }
        super.run(notifier);
    }
}
