package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criteria.SourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.WatchListTestsUtil;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(E2eTestRunner.class)
public class DeleteWatchListPageTest {

    private static WatchListTestsUtil watchListTestsUtil;
    private static SourcePointCriteria watchListToDeleteCriteria;
    private static WatchListPage watchListPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecound();
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(123);

        watchListToDeleteCriteria = new SourcePointCriteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

        watchListTestsUtil = new WatchListTestsUtil(navigationPage, watchListToDeleteCriteria);
        watchListTestsUtil.init();
        watchListPageSubject = watchListTestsUtil.addWatchLists();
    }

    @AfterClass
    public static void clean() {
        watchListTestsUtil.clean();
    }

    @Test
    public void test_delete_watch_list() {

        //when:
        String watchListBeforeDelete = watchListPageSubject.getWatchListText();

        //then:
        assertThat(watchListBeforeDelete, containsString(watchListToDeleteCriteria.getIdentifier()));

        //and when:
        watchListPageSubject.deleteFromWatchList(watchListToDeleteCriteria);

        //and:
        String watchListAfterDelete = watchListPageSubject.getWatchListText();

        //then:
        assertThat(watchListAfterDelete, not(containsString(watchListToDeleteCriteria.getIdentifier())));
    }
}
