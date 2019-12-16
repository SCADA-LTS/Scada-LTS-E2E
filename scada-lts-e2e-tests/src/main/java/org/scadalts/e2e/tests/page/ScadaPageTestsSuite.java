package org.scadalts.e2e.tests.page;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.tests.page.datasource.DataSourceTestsSuite;
import org.scadalts.e2e.tests.page.graphicalviews.GraphicalViewTestsSuite;
import org.scadalts.e2e.tests.page.home.HomePageTestsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HomePageTestsSuite.class,
        DataSourceTestsSuite.class,
        GraphicalViewTestsSuite.class
})
public class ScadaPageTestsSuite {

}
