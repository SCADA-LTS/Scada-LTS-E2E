package org.scadalts.e2e.test.impl.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;
import org.scadalts.e2e.test.impl.tests.check.login.LoginCheckTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginCheckTest.class,
        EventDetectorCheckTest.class
})
public class ScadaProdTestsSuite {
}
