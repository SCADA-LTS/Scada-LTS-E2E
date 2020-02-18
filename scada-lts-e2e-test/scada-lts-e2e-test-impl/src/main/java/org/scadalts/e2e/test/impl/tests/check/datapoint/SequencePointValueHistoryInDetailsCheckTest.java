package org.scadalts.e2e.test.impl.tests.check.datapoint;


import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criteria.WatchListCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.ListLimitedOnlyMethodAddSupported;
import org.scadalts.e2e.test.impl.utils.WatchListTestsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@Log4j2
@RunWith(E2eTestParameterizedRunner.class)
public class SequencePointValueHistoryInDetailsCheckTest {

    @Parameterized.Parameters(name = "{index}:{0}")
    public static Collection<?> data() {
        return WatchListTestsUtil.paramsToTests();
    }

    private final String valueExpected;

    public SequencePointValueHistoryInDetailsCheckTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static DataPointDetailsPage dataPointDetailsPageSubject;
    private static ListLimitedOnlyMethodAddSupported<String> listExcepted;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = new DataSourceCriteria(TestImplConfiguration.dataSourceName, DataSourceType.NONE, UpdatePeriodType.NONE);
        DataPointCriteria dataPointCriteria = new DataPointCriteria(TestImplConfiguration.dataPointName, DataPointType.NUMERIC, ChangeType.NO_CHANGE);
        WatchListCriteria watchListCriteria = new WatchListCriteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

        WatchListTestsUtil watchListTestsUtil = new WatchListTestsUtil(navigationPage, watchListCriteria);
        dataPointDetailsPageSubject = watchListTestsUtil.openWatchListPage()
                .openDataPointDetails(watchListCriteria);
        int limit = dataPointDetailsPageSubject.getHistoryLimit();
        List<String> result = dataPointDetailsPageSubject.getValuesFromHistory();

        listExcepted = new ListLimitedOnlyMethodAddSupported<>(limit);
        listExcepted.addAll(result);
    }

    @AfterClass
    public static void clean() {
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