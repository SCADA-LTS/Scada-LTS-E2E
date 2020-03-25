package org.scadalts.e2e.test.impl.tests.service.pointlinks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PointLinksChangeServiceTest.class,
        PointLinksUpdateServiceTest.class,
        PointLinksMultiParametersServiceTest.class,
        PointLinksWithDataSourceServiceTest.class
})
public class PointLinksServiceTestsSuite {
}
