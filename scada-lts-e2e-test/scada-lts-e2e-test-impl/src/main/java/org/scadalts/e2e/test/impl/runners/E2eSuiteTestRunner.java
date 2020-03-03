package org.scadalts.e2e.test.impl.runners;

import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

import java.util.List;

public class E2eSuiteTestRunner extends Suite {

    public E2eSuiteTestRunner(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
    }

    public E2eSuiteTestRunner(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
        super(builder, classes);
    }

    public E2eSuiteTestRunner(Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
        super(klass, suiteClasses);
    }

    public E2eSuiteTestRunner(RunnerBuilder builder, Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
        super(builder, klass, suiteClasses);
    }

    public E2eSuiteTestRunner(Class<?> klass, List<Runner> runners) throws InitializationError {
        super(klass, runners);
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            if (!E2eAbstractRunnable.isLogged()) {
                E2eAbstractRunnable.setup();
                E2eAbstractRunnable.login();
            }
        } catch (Throwable throwable) {
            NavigationPage.kill();
            throw throwable;
        }
        super.run(notifier);
    }
}
