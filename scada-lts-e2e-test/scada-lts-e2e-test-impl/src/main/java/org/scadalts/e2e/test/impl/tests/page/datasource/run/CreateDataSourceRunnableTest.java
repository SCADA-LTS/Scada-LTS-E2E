package org.scadalts.e2e.test.impl.tests.page.datasource.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.page.datasource.CreateDataSourceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateDataSourceTest.class
})
public class CreateDataSourceRunnableTest extends E2eAbstractRunnable {
}
