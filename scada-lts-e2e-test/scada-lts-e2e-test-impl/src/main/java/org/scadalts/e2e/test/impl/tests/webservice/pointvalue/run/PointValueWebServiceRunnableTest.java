package org.scadalts.e2e.test.impl.tests.webservice.pointvalue.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.webservice.pointvalue.PointValueWebServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PointValueWebServiceTest.class
})
public class PointValueWebServiceRunnableTest extends E2eAbstractRunnable {
}
