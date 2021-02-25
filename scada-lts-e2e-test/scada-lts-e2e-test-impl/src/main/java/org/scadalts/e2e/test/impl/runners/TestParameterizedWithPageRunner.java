package org.scadalts.e2e.test.impl.runners;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@Log4j2
@Deprecated
public class TestParameterizedWithPageRunner extends Parameterized {
    public TestParameterizedWithPageRunner(Class<?> klass) throws Throwable {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        TestWithPageUtil.preparingTest();
        super.run(notifier);
        if(!E2eConfiguration.checkAuthentication)
            TestWithPageUtil.logout();
    }
}
