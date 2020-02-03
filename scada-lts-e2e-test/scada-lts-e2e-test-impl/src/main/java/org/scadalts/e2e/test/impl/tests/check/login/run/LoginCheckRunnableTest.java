package org.scadalts.e2e.test.impl.tests.check.login.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.check.login.LoginCheckTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginCheckTest.class
})
public class LoginCheckRunnableTest extends E2eAbstractRunnable {
}
