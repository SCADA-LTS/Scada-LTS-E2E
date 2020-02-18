package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateDataSourceTest.class,
        DeleteDataSourceTest.class,
        EditDataSourceTest.class
})
public class DataSourceTestsSuite {
}
