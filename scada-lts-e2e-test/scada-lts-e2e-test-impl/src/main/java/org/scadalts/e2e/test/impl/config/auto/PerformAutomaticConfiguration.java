package org.scadalts.e2e.test.impl.config.auto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.*;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.ConfigForTestDataPointDetailsCheck;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.ConfigForTestGraphicalViewsCheck;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.ConfigForTestEventDetectorCheck;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.ConfigForTestPointLinksCheck;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

@RunWith(E2eTestParameterizedRunner.class)
public class PerformAutomaticConfiguration {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Task<?>[] data() {
        if(!E2eAbstractRunnable.isLogged()) {
            E2eAbstractRunnable.setup();
            E2eAbstractRunnable.login();
        }
        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
        return new Task<?>[] {
                new ConfigureTestEventDetectorCommand(navigationPage),
                new ConfigureTestPointLinksCommand(navigationPage),
                new ConfigureTestGraphicalViewsCommand(navigationPage),
                new ConfigureTestDataPointDetailsCommand(navigationPage),

                new ConfigForTestDataPointDetailsCheck(navigationPage),
                new ConfigForTestGraphicalViewsCheck(navigationPage),
                new ConfigForTestEventDetectorCheck(navigationPage),
                new ConfigForTestPointLinksCheck(navigationPage)

        };
    }

    private final Task<?> task;

    public PerformAutomaticConfiguration(Task<?> task) {
        this.task = task;
    }

    @Test
    public void configure() {
        task.execute();
    }
}
