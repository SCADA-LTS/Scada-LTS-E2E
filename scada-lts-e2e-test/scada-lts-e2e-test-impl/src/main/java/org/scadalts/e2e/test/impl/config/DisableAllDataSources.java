package org.scadalts.e2e.test.impl.config;

import org.junit.BeforeClass;
import org.junit.Test;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.utils.DisableAllDataSourcesTask;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

public class DisableAllDataSources {

    private static NavigationPage navigationPage;

    @BeforeClass
    public static void setup() {
        navigationPage = TestWithPageUtil.openNavigationPage();
    }

    @Test
    public void disable_all_data_sources() {
        new DisableAllDataSourcesTask(navigationPage).execute();
    }
}
