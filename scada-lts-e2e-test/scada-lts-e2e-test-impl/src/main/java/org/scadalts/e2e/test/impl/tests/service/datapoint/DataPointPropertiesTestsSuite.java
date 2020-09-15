package org.scadalts.e2e.test.impl.tests.service.datapoint;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataPointChartRendererPropertiesServiceTest.class,
        DataPointLoggingPropertiesServiceTest.class,
        DataPointTextRendererPropertiesServiceTest.class,
        DataPointUnitsPropertiesServiceTest.class,
        EventDetectorPropertiesServiceTest.class
})
public class DataPointPropertiesTestsSuite {
}
