package org.scadalts.e2e.test.impl.tests.page;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.page.datasource.DataSourcePageTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.DataPointPageTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.eventhandlers.EventHandlersPageTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.graphicalviews.GraphicalViewPageTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.navigation.NavigationPageTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.pointlinks.PointLinksPageTestsSuite;
import org.scadalts.e2e.test.impl.tests.page.watchlist.WatchListPageTestsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        NavigationPageTestsSuite.class,
        DataSourcePageTestsSuite.class,
        DataPointPageTestsSuite.class,
        GraphicalViewPageTestsSuite.class,
        WatchListPageTestsSuite.class,
        PointLinksPageTestsSuite.class,
        EventHandlersPageTestsSuite.class
})
public class ScadaPageTestsSuite {

}
