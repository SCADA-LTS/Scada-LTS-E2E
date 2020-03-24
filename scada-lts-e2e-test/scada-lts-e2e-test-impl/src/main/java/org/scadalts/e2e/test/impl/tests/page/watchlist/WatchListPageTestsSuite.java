package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.page.watchlist.pointdetails.DataPointDetailsPageTestsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DeleteWatchListPageTest.class,
        ChangePointValueOnWatchListPageTest.class,
        DataPointDetailsPageTestsSuite.class
})
public class WatchListPageTestsSuite {
}
