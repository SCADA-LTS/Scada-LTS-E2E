package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.eventdetectors.CreateEventDetectorPageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateDataPointPageTest.class,
        DeleteDataPointPageTest.class,
        CreateEventDetectorPageTest.class
})
public class DataPointPageTestsSuite {
}
