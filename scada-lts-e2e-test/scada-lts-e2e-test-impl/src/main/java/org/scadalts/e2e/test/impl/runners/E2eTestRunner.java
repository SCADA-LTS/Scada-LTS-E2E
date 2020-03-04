package org.scadalts.e2e.test.impl.runners;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import static org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable.preparingPageTest;

@Log4j2
public class E2eTestRunner extends BlockJUnit4ClassRunner {

    public E2eTestRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            preparingPageTest();
        } catch (Throwable throwable) {
            NavigationPage.kill();
            throw throwable;
        }
        super.run(notifier);
    }

}
