package org.scadalts.e2e.test.impl.tests.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.service.pointlinks.PointLinksServiceTestsSuite;
import org.scadalts.e2e.test.impl.tests.service.pointvalue.PointValueServiceTest;
import org.scadalts.e2e.test.impl.tests.service.set.CmpServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PointValueServiceTest.class,
        PointLinksServiceTestsSuite.class,
        CmpServiceTest.class
})
public class ScadaServiceTestsSuite {
}
