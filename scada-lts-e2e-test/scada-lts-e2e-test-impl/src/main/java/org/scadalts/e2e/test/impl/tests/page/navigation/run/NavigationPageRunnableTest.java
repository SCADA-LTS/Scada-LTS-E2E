package org.scadalts.e2e.test.impl.tests.page.navigation.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.page.navigation.NavigationPageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        NavigationPageTest.class
})
public class NavigationPageRunnableTest extends E2eAbstractRunnable {
}
