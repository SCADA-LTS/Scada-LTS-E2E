package org.scadalts.e2e.test.impl.tests.webservice;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.webservice.pointvalue.PointValueWebServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PointValueWebServiceTest.class
})
public class ScadaWebServiceTestsRunnableSuite extends E2eAbstractRunnable {
}
