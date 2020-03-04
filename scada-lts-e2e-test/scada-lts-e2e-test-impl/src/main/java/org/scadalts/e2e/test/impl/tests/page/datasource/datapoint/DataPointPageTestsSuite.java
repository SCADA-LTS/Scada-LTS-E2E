package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateDataPointPageTest.class,
        DeleteDataPointPageTest.class,
        ChangePointValueInDetailsPageTest.class,
        SequencePointValueHistoryInDetailsPageTest.class,
        CreateEventDetectorPageTest.class
})
public class DataPointPageTestsSuite {
}
