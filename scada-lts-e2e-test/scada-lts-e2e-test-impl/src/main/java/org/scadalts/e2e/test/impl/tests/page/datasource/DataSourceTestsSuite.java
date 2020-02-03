package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.DataPointTestsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateDataSourceTest.class,
        DeleteDataSourceTest.class,
        EditDataSourceTest.class,
        DataPointTestsSuite.class
})
public class DataSourceTestsSuite {
}
