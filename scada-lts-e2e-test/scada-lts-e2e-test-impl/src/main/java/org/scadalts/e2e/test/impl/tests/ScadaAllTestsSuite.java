package org.scadalts.e2e.test.impl.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.check.ScadaCheckTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.ScadaPageTestsSuite;
import org.scadalts.e2e.test.impl.tests.service.ScadaServiceTestsSuite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ScadaPageTestsSuite.class,
        ScadaServiceTestsSuite.class,
        ScadaCheckTestsSuite.class
})
public class ScadaAllTestsSuite {
}
