package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criteria.WatchListCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourcesAndPointsPageTestsUtil;
import org.scadalts.e2e.test.impl.utils.WatchListTestsUtil;

import static org.junit.Assert.*;

@RunWith(E2eTestRunner.class)
public class DeleteWatchListPageTest {

    private static DataSourcesAndPointsPageTestsUtil dataSourcesPageTestsUtil;
    private static WatchListCriteria watchListCriteria;
    private static WatchListPage watchListPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourcesAndPointsPageTestsUtil.createDataSourceCriteria();
        DataPointCriteria dataPointCriteria = new DataPointCriteria("dp_test" + System.nanoTime(), DataPointType.NUMERIC, ChangeType.NO_CHANGE);
        watchListCriteria = new WatchListCriteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

        dataSourcesPageTestsUtil = new DataSourcesAndPointsPageTestsUtil(navigationPage, dataSourceCriteria, dataPointCriteria);
        dataSourcesPageTestsUtil.init("123");

        WatchListTestsUtil watchListTestsUtil = new WatchListTestsUtil(navigationPage, watchListCriteria);
        watchListPageSubject = watchListTestsUtil.openWatchListPage();
        watchListPageSubject.addToWatchList(watchListCriteria);
    }

    @AfterClass
    public static void clean() {
        dataSourcesPageTestsUtil.clean();
    }

    @Test
    public void test_delete_watch_list() {

        //when:
        boolean existsBeforeDelete = watchListPageSubject.containsObject(watchListCriteria);

        //then:
        assertEquals(true, existsBeforeDelete);

        //and when:
        watchListPageSubject.deleteFromWatchList(watchListCriteria);

        //and:
        boolean result = watchListPageSubject.reopen().containsObject(watchListCriteria);

        //then:
        assertEquals(false, result);
    }
}
