package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.DeleteDataPointTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DeleteDataPointTest.class
})
public class DeleteDataPointRunnableTest extends E2eAbstractRunnable {
}
