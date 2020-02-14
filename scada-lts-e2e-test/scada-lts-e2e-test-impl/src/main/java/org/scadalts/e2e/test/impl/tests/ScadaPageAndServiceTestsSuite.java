package org.scadalts.e2e.test.impl.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.page.ScadaPageTestsSuite;
import org.scadalts.e2e.test.impl.tests.webservice.ScadaWebServiceTestsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ScadaPageTestsSuite.class,
        ScadaWebServiceTestsSuite.class
})
public class ScadaPageAndServiceTestsSuite {
}
