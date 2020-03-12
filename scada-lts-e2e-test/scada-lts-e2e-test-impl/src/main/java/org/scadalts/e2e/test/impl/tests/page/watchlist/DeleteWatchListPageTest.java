package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(TestWithPageRunner.class)
public class DeleteWatchListPageTest {

    private static CreatorObject<WatchListPage, WatchListPage> watchListTestsUtil;
    private static CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcePointObjectsCreator;

    private static DataSourcePointCriteria watchListToDeleteCriteria;
    private static WatchListPage watchListPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(123);

        watchListToDeleteCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();

        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, watchListToDeleteCriteria);
        dataSourcePointObjectsCreator.createObjects();

        watchListTestsUtil = new WatchListObjectsCreator(navigationPage, watchListToDeleteCriteria);
        watchListPageSubject = watchListTestsUtil.createObjects();
    }

    @AfterClass
    public static void clean() {
        watchListTestsUtil.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
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
