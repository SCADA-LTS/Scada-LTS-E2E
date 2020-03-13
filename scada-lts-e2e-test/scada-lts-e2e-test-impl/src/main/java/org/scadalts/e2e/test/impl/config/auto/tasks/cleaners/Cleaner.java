package org.scadalts.e2e.test.impl.config.auto.tasks.cleaners;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.Task;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@RunWith(Parameterized.class)
public class Cleaner {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Task[] data() {
        NavigationPage navigationPage = TestWithPageUtil.preparingTest();
        return new Task[] {
                new DeleteAllDataSourcesForTestTask(navigationPage),
                new DeleteAllGraphicalViewsForTestTask(navigationPage)
        };
    }

    private final Task task;

    public Cleaner(Task task) {
        this.task = task;
    }

    @Test
    public void clean() {
        task.execute();
    }
}
