package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DeleteWatchListPageTest.class,
        ChangePointValueOnWatchListPageTest.class,
        ChangePointValueViaPointLinksOneDataSourcePageTest.class,
        ChangePointValueViaPointLinksTwoDataSourcePageTest.class,
        UpdatePointValueViaPointLinksOneDataSourcePageTest.class,
        UpdatePointValueViaPointLinksTwoDataSourcePageTest.class,
        ChangePointValueInDetailsPageTest.class,
        SequencePointValueHistoryInDetailsPageTest.class,
        AnnotationsChangePointValuePageTest.class,
        AnnotationsChangeValueIfPointDisabledPageTest.class
})
public class WatchListPageTestsSuite {
}
