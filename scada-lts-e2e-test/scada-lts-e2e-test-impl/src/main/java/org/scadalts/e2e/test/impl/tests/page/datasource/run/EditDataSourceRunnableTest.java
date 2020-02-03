package org.scadalts.e2e.test.impl.tests.page.datasource.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.page.datasource.EditDataSourceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EditDataSourceTest.class
})
public class EditDataSourceRunnableTest extends E2eAbstractRunnable {
}
