package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.utils.TestObjectsUtilible;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourcePointTestObjectsUtil;
import org.scadalts.e2e.test.impl.utils.WatchListTestObjectsUtil;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(E2eTestRunner.class)
public class DeleteWatchListPageTest {

    private static TestObjectsUtilible<WatchListPage, WatchListPage> watchListTestsUtil;
    private static TestObjectsUtilible<DataSourcesPage, EditDataSourceWithPointListPage> dataSourcesAndPointsPageTestsUtil;

    private static DataSourcePointCriteria watchListToDeleteCriteria;
    private static WatchListPage watchListPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(123);

        watchListToDeleteCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

        dataSourcesAndPointsPageTestsUtil = new DataSourcePointTestObjectsUtil(navigationPage, watchListToDeleteCriteria);
        dataSourcesAndPointsPageTestsUtil.createObjects();

        watchListTestsUtil = new WatchListTestObjectsUtil(navigationPage, watchListToDeleteCriteria);
        watchListPageSubject = watchListTestsUtil.createObjects();
    }

    @AfterClass
    public static void clean() {
        watchListTestsUtil.deleteObjects();
        dataSourcesAndPointsPageTestsUtil.deleteObjects();
    }

    @Test
    public void test_delete_watch_list() {

        //when:
        String watchListBeforeDelete = watchListPageSubject.getWatchListText();

        //then:
        assertThat(watchListBeforeDelete, containsString(watchListToDeleteCriteria.getIdentifier().getValue()));

        //and when:
        watchListPageSubject.deleteFromWatchList(watchListToDeleteCriteria);

        //and:
        String watchListAfterDelete = watchListPageSubject.getWatchListText();

        //then:
        assertThat(watchListAfterDelete, not(containsString(watchListToDeleteCriteria.getIdentifier().getValue())));
    }
}
