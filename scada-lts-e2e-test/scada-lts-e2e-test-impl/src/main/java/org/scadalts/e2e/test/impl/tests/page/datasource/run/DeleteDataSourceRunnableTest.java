package org.scadalts.e2e.test.impl.tests.page.datasource.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.page.datasource.DeleteDataSourceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DeleteDataSourceTest.class
})
public class DeleteDataSourceRunnableTest extends E2eAbstractRunnable {
}
