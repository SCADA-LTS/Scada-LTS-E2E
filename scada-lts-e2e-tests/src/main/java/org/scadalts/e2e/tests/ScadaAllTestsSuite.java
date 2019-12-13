package org.scadalts.e2e.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.tests.api.ScadaApiTestsSuite;
import org.scadalts.e2e.tests.check.ScadaCheckTestsSuite;
import org.scadalts.e2e.tests.page.ScadaPageTestsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ScadaPageTestsSuite.class,
        ScadaApiTestsSuite.class,
        ScadaCheckTestsSuite.class
})
public class ScadaAllTestsSuite {
}
