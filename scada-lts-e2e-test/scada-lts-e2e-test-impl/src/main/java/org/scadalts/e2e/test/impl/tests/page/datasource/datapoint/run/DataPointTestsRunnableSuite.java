package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.CreateDataPointTest;
import org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.DeleteDataPointTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateDataPointTest.class,
        DeleteDataPointTest.class
})
public class DataPointTestsRunnableSuite extends E2eAbstractRunnable {
}
