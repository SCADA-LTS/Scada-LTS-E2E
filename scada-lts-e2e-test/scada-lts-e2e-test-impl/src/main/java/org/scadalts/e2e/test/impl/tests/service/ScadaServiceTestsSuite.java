package org.scadalts.e2e.test.impl.tests.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.service.storungs.StorungsAndAlarmsServiceTestsSuite;
import org.scadalts.e2e.test.impl.tests.service.cmp.CmpServiceTest;
import org.scadalts.e2e.test.impl.tests.service.datapoint.DataPointPropertiesTestsSuite;
import org.scadalts.e2e.test.impl.tests.service.pointlinks.PointLinksServiceTestsSuite;
import org.scadalts.e2e.test.impl.tests.service.pointvalue.PointValueServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PointValueServiceTest.class,
        PointLinksServiceTestsSuite.class,
        CmpServiceTest.class,
        DataPointPropertiesTestsSuite.class,
        StorungsAndAlarmsServiceTestsSuite.class
})
public class ScadaServiceTestsSuite {
}
