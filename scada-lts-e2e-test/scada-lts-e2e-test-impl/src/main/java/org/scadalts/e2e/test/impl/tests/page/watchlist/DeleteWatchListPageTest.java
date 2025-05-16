package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class DeleteWatchListPageTest {

    private static CreatorObject<WatchListPage, WatchListPage> watchListObjectsCreator;
    private static CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcePointObjectsCreator;

    private static VirtualDataSourcePointCriteria dataSourcePointToDeleteCriteria;
    private static WatchListPage watchListPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.virtualDataSourceSecond();
        VirtualDataPointCriteria dataPointCriteria = VirtualDataPointCriteria.numericNoChange(123);

        dataSourcePointToDeleteCriteria = VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();

        dataSourcePointObjectsCreator = new VirtualDataSourcePointObjectsCreator(navigationPage, dataSourcePointToDeleteCriteria);
        dataSourcePointObjectsCreator.createObjects();

        WatchListCriteria watchListToDeleteCriteria = WatchListCriteria.criteria(dataSourcePointToDeleteCriteria.getIdentifier());
        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, watchListToDeleteCriteria);
        watchListPageSubject = watchListObjectsCreator.createObjects()
                .selectWatchList(watchListToDeleteCriteria.getIdentifier());
    }

    @AfterClass
    public static void clean() {
        if(watchListObjectsCreator != null)
            watchListObjectsCreator.deleteObjects();
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_delete_watch_list() {

        //when:
        String watchListBeforeDelete = watchListPageSubject.getWatchListText();

        //then:
        assertThat(watchListBeforeDelete, containsString(dataSourcePointToDeleteCriteria.getIdentifier().getValue()));

        //and when:
        watchListPageSubject.deleteFromWatchList(dataSourcePointToDeleteCriteria.getIdentifier());

        //and:
        String watchListAfterDelete = watchListPageSubject.getWatchListText();

        //then:
        assertThat(watchListAfterDelete, not(containsString(dataSourcePointToDeleteCriteria.getIdentifier().getValue())));
    }
}
