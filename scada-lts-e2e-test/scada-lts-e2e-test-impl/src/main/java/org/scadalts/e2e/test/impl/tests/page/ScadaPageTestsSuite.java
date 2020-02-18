package org.scadalts.e2e.test.impl.tests.page;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.page.datasource.DataSourceTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.DataPointTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.graphicalviews.GraphicalViewTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.navigation.NavigationPageTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.watchlist.WatchListPageTestsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        NavigationPageTestsSuite.class,
        DataSourceTestsSuite.class,
        DataPointTestsSuite.class,
        GraphicalViewTestsSuite.class,
        WatchListPageTestsSuite.class
})
public class ScadaPageTestsSuite {

}
