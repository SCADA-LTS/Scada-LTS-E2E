package org.scadalts.e2e.test.impl.tests.page.watchlist.pointdetails;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.page.watchlist.ChangePointValueOnWatchListPageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AnnotationsChangePointValuePageTest.class,
        //AnnotationsChangeValueIfPointDisabledPageTest.class,
        ChangePointValueInDetailsPageTest.class,
        //SequencePointValueHistoryInDetailsPageTest.class
})
public class DataPointDetailsPageTestsSuite {
}
