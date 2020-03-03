package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;


import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.ListLimitedOnlyMethodAddSupported;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@Log4j2
@RunWith(E2eTestParameterizedRunner.class)
public class SequencePointValueHistoryInDetailsPageTest {

    @Parameterized.Parameters(name = "{index}: value:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String valueExpected;

    public SequencePointValueHistoryInDetailsPageTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static CreatorObject<WatchListPage, WatchListPage> watchListTestsUtil;
    private static CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcesAndPointsPageTestsUtil;
    private static DataPointDetailsPage dataPointDetailsPageSubject;
    private static ListLimitedOnlyMethodAddSupported<String> listExcepted;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(DataPointType.NUMERIC, "123.0");
        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria
                .criteria(dataSourceCriteria, dataPointCriteria);

        dataSourcesAndPointsPageTestsUtil = new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcesAndPointsPageTestsUtil.createObjects();

        watchListTestsUtil = new WatchListObjectsCreator(E2eAbstractRunnable.getNavigationPage(), dataSourcePointCriteria);
        dataPointDetailsPageSubject = watchListTestsUtil.createObjects()
                .openDataPointDetails(dataSourcePointCriteria);

        int limit = dataPointDetailsPageSubject.getHistoryLimit();
        List<String> result = dataPointDetailsPageSubject.getValuesFromHistory();

        listExcepted = new ListLimitedOnlyMethodAddSupported<>(limit);
        listExcepted.addAll(result);
    }

    @AfterClass
    public static void clean() {
        watchListTestsUtil.deleteObjects();
        dataSourcesAndPointsPageTestsUtil.deleteObjects();
        listExcepted.clear();
    }

    @Test
    public void test_sequence_history_change_point_value() {

        //given:
        listExcepted.add(valueExpected);

        //when:
        dataPointDetailsPageSubject
                .setDataPointValue(valueExpected)
                .confirmDataPointValue();

        //and:
        List<String> result = dataPointDetailsPageSubject.refreshPage().getValuesFromHistory();

        //then:
        assertNotNull(result);
        assertNotEquals(Collections.emptyList(), result);
        assertEquals(listExcepted, result);
    }

}