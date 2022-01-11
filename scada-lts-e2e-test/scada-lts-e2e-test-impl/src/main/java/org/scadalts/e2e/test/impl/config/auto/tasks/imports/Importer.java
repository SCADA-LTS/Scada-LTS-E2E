package org.scadalts.e2e.test.impl.config.auto.tasks.imports;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.Task;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@RunWith(Parameterized.class)
public class Importer {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Task[] data() {
        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();
        return new Task[] {
                new ImportDataSourcesWithEnabledTask(navigationPage)
        };
    }

    private final Task task;

    public Importer(Task task) {
        this.task = task;
    }

    @Test
    public void configure() {
        task.execute();
    }
}
