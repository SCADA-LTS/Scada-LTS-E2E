package org.scadalts.e2e.test.impl.tests.check;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;
import org.scadalts.e2e.test.impl.tests.check.graphicalviews.GraphicalViewsCheckTestsSuite;
import org.scadalts.e2e.test.impl.tests.check.login.LoginCheckTestsSuite;
import org.scadalts.e2e.test.impl.tests.check.storungs.GetAllLivesParametersCheckTest;
import org.scadalts.e2e.test.impl.tests.check.user.GetAllUserCheckTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginCheckTestsSuite.class,
        EventDetectorCheckTest.class,
        GraphicalViewsCheckTestsSuite.class,
        //DataPointDetailsCheckTestsSuite.class,
        //ChangePointValueViaPointLinksCheckTest.class,
        GetAllLivesParametersCheckTest.class,
        GetAllUserCheckTest.class
})
public class ScadaCheckTestsSuite {
}
