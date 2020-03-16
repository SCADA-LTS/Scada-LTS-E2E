package org.scadalts.e2e.test.impl.runners;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@Log4j2
public class TestParameterizedWithPageRunner extends Parameterized {
    public TestParameterizedWithPageRunner(Class<?> klass) throws Throwable {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            TestWithPageUtil.preparingTest();
        } catch (Throwable ex) {
            TestWithPageUtil.close();
            throw ex;
        }
        super.run(notifier);
    }
}
