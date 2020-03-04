package org.scadalts.e2e.test.impl.runners;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import static org.scadalts.e2e.test.impl.utils.ServiceTestsUtil.preparingServiceTest;

@Log4j2
public class E2eServiceTestParameterizedRunner extends Parameterized {

    public E2eServiceTestParameterizedRunner(Class<?> clazz) throws Throwable {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            preparingServiceTest();
        } catch (Throwable throwable) {
            NavigationPage.kill();
            throw throwable;
        }
        super.run(notifier);
    }
}