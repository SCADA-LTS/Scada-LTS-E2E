package org.scadalts.e2e.test.impl.runners;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.test.core.exceptions.E2eTestException;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

public class TestParameterizedWithoutPageRunner extends Parameterized {
    public TestParameterizedWithoutPageRunner(Class<?> klass) throws Throwable {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            TestWithoutPageUtil.preparingTest();
        } catch (Throwable throwable) {
            TestWithoutPageUtil.close();
            throw new E2eTestException(throwable.getMessage(), throwable);
        }
        super.run(notifier);
    }
}
