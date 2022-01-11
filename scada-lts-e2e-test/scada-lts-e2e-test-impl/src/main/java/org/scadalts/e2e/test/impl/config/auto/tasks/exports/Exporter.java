package org.scadalts.e2e.test.impl.config.auto.tasks.exports;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.Task;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@RunWith(Parameterized.class)
public class Exporter {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Task[] data() {
        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();
        return new Task[] {
                new ExportDataSourcesWithEnabledToJsonTask(navigationPage)
        };
    }

    private final Task task;

    public Exporter(Task task) {
        this.task = task;
    }

    @Test
    public void configure() {
        task.execute();
    }
}
