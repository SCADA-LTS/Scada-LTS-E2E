package org.scadalts.e2e.test.impl.config.auto;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.Task;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.ConfigForTestEventDetectorCheck;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@Log4j2
@RunWith(Parameterized.class)
public class ConfigForProdChecker {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Task[] data() {
        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();
        return new Task[] {
                new ConfigForTestEventDetectorCheck(navigationPage)
        };
    }

    private final Task task;

    public ConfigForProdChecker(Task task) {
        this.task = task;
    }

    @Test
    public void configure() {
        task.execute();
    }
}
