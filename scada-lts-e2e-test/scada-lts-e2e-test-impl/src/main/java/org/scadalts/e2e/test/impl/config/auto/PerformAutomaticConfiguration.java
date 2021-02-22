package org.scadalts.e2e.test.impl.config.auto;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.config.auto.tasks.Task;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.ConfigureTestDataPointDetailsCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.ConfigureTestEventDetectorCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.ConfigureTestGraphicalViewsCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.ConfigureTestPointLinksCommand;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@RunWith(Parameterized.class)
public class PerformAutomaticConfiguration {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Task[] data() {
        NavigationPage navigationPage = TestWithPageUtil.preparingTest();
        return new Task[] {
                new ConfigureTestEventDetectorCommand(navigationPage),
                new ConfigureTestDataPointDetailsCommand(navigationPage),
                new ConfigureTestGraphicalViewsCommand(navigationPage),
                new ConfigureTestPointLinksCommand(navigationPage)
        };
    }

    private final Task task;

    public PerformAutomaticConfiguration(Task task) {
        this.task = task;
    }

    @BeforeClass
    public static void clear() {
        CriteriaRegisterAggregator.INSTANCE.clear();
    }

    @Test
    public void configure() {
        task.execute();
    }
}
