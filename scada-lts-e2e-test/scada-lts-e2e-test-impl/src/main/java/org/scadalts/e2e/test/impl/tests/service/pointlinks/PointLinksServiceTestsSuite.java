package org.scadalts.e2e.test.impl.tests.service.pointlinks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ChangePointValueViaPointLinksOneDataSourceServiceTest.class,
        ChangePointValueViaPointLinksTwoDataSourceServiceTest.class,
        UpdatePointValueViaPointLinksOneDataSourceServiceTest.class,
        UpdatePointValueViaPointLinksTwoDataSourceServiceTest.class,
        ChangePointValueViaPointLinksServiceTest.class
})
public class PointLinksServiceTestsSuite {
}
