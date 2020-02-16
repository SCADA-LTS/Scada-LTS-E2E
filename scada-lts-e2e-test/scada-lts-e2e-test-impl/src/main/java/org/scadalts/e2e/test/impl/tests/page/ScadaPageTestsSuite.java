package org.scadalts.e2e.test.impl.tests.page;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.page.datasource.DataSourceTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.graphicalviews.GraphicalViewTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.navigation.NavigationPageTest;
import org.scadalts.e2e.test.impl.tests.page.watchlist.ChangePointValueOnWatchListPageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        NavigationPageTest.class,
        DataSourceTestsSuite.class,
        GraphicalViewTestsSuite.class,
        ChangePointValueOnWatchListPageTest.class
})
public class ScadaPageTestsSuite {

}
