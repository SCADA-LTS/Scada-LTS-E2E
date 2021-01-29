package org.scadalts.e2e.test.impl.runners;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

@Log4j2
public class TestWithoutPageRunner extends BlockJUnit4ClassRunner {
    public TestWithoutPageRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            TestWithoutPageUtil.preparingTest();
        } catch (Throwable ex) {
            TestWithoutPageUtil.close();
            throw ex;
        }
        super.run(notifier);
        if(!E2eConfiguration.checkAuthentication)
            TestWithoutPageUtil.logout();
    }
}
