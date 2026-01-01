package org.scadalts.e2e.test.impl.tests.page.watchlist.pointdetails;


import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.Collection;

import static org.junit.Assert.*;

@Log4j2
@RunWith(Parameterized.class)
public class ChangePointValueInDetailsPageTest {

    @Parameterized.Parameters(name = "{index}: expected:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String valueExpected;

    public ChangePointValueInDetailsPageTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static CreatorObject<WatchListPage, WatchListPage> watchListObjectsCreator;
    private static CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcePointObjectsCreator;
    private static DataPointDetailsPage dataPointDetailsPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.virtualDataSourceSecond();
        VirtualDataPointCriteria dataPointCriteria = VirtualDataPointCriteria.numericNoChange(123);

        VirtualDataSourcePointCriteria dataSourcePointCriteria = VirtualDataSourcePointCriteria
                .virtualCriteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();

        dataSourcePointObjectsCreator =
                new VirtualDataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcePointObjectsCreator.createObjects();

        WatchListCriteria watchListCriteria = WatchListCriteria.criteria(dataSourcePointCriteria.getIdentifier());
        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, watchListCriteria);
        dataPointDetailsPageSubject = watchListObjectsCreator.createObjects()
                .selectWatchList(watchListCriteria.getIdentifier())
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier());
    }

    @AfterClass
    public static void clean() {
        if(watchListObjectsCreator != null)
            watchListObjectsCreator.deleteObjects();
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_change_data_point_value_in_details() {

        //when:
        dataPointDetailsPageSubject
                .setDataPointValue(valueExpected)
                .confirmDataPointValue();

        //and:
        String result = dataPointDetailsPageSubject.refreshPage().getDataPointValue(valueExpected);

        //then:
        assertNotNull(result);
        assertNotEquals("", result);

        //then:
        assertEquals(valueExpected, result);
    }
}