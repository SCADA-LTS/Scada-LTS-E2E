package org.scadalts.e2e.test.impl.tests.page.watchlist;


import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.Collection;

import static org.junit.Assert.*;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class ChangePointValueInDetailsPageTest {

    @Parameterized.Parameters(name = "{index}: expected:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String valueExpected;

    public ChangePointValueInDetailsPageTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static CreatorObject<WatchListPage, WatchListPage> watchListTestsUtil;
    private static CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcesAndPointsPageTestsUtil;
    private static DataPointDetailsPage dataPointDetailsPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(123);

        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria
                .criteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();

        dataSourcesAndPointsPageTestsUtil =
                new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcesAndPointsPageTestsUtil.createObjects();

        watchListTestsUtil = new WatchListObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataPointDetailsPageSubject = watchListTestsUtil.createObjects()
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier());
    }

    @AfterClass
    public static void clean() {
        watchListTestsUtil.deleteObjects();
        dataSourcesAndPointsPageTestsUtil.deleteObjects();
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