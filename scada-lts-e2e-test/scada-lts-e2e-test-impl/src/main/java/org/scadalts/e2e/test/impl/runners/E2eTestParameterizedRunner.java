package org.scadalts.e2e.test.impl.runners;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

@Log4j2
public class E2eTestParameterizedRunner extends Parameterized {

    public E2eTestParameterizedRunner(Class<?> clazz) throws Throwable {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            if (!E2eAbstractRunnable.isLogged()) {
                E2eAbstractRunnable.setup();
            }
        } catch (Throwable throwable) {
            NavigationPage.kill();
            throw throwable;
        }
        super.run(notifier);
    }
}
