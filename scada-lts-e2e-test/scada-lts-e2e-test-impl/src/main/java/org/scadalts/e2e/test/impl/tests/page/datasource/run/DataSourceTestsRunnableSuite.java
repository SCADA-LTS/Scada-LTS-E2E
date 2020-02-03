package org.scadalts.e2e.test.impl.tests.page.datasource.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.page.datasource.CreateDataSourceTest;
import org.scadalts.e2e.test.impl.tests.page.datasource.DeleteDataSourceTest;
import org.scadalts.e2e.test.impl.tests.page.datasource.EditDataSourceTest;
import org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.DataPointTestsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateDataSourceTest.class,
        DeleteDataSourceTest.class,
        EditDataSourceTest.class,
        DataPointTestsSuite.class
})
public class DataSourceTestsRunnableSuite extends E2eAbstractRunnable {
}
