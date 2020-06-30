package org.scadalts.e2e.test.impl.tests.service.alarms;

import lombok.extern.log4j.Log4j2;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.creators.DataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestDataBatch;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.*;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class SequenceAlarmStorungLiveServiceTest {

    @Parameterized.Parameters(name = "{index}: sequence: {0}")
    public static List<TestDataBatch> data() {
        List<TestDataBatch> result = new ArrayList<>();

        result.addAll(generateDataTest(3, DataPointNotifierType.ALARM, 0));
        result.addAll(generateDataTest(3, DataPointNotifierType.ALARM, 1));
        result.addAll(generateDataTest(3, DataPointNotifierType.STORUNG, 0));
        result.addAll(generateDataTest(3, DataPointNotifierType.STORUNG, 1));
        result.addAll(generateDataTest(3, DataPointNotifierType.NONE, 0));
        result.addAll(generateDataTest(3, DataPointNotifierType.NONE, 1));

        return result;
    }

    private final TestDataBatch testDataBatch;

    public SequenceAlarmStorungLiveServiceTest(TestDataBatch testDataBatch) {
        this.testDataBatch = testDataBatch;
    }

    private PaginationParams paginationParams = PaginationParams.builder()
            .limit(9999)
            .offset(0)
            .build();


    private static DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
    private static DataSourcePointObjectsCreator dataSourcePointObjectsCreator;

    private WatchListObjectsCreator watchListObjectsCreator;
    private DataPointObjectsCreator dataPointObjectsCreator;
    private WatchListPage watchListPage;
    private DataSourcePointIdentifier dataSourcePointIdentifier;

    @BeforeClass
    public static void createDataSource() {
        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria);
        dataSourcePointObjectsCreator.createObjects();
    }

    @Before
    public void setup() {

        DataPointCriteria point = DataPointCriteria.noChange(testDataBatch.getDataPointIdentifier(),
                String.valueOf(testDataBatch.getStartValue()));

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        dataPointObjectsCreator = new DataPointObjectsCreator(navigationPage, dataSourceCriteria, point);
        dataPointObjectsCreator.createObjects();

        dataSourcePointIdentifier = new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(),
                testDataBatch.getDataPointIdentifier());

        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, WatchListCriteria.criteria(dataSourcePointIdentifier));
        watchListObjectsCreator.createObjects();

        watchListPage = navigationPage.openWatchList();

        List<AlarmResponse> alarmResponses = getAlarms(testDataBatch.getDataPointIdentifier(), paginationParams);
        String msg = MessageFormat.format(AFTER_INITIALIZING_POINT_VALUE_WITH_X_THEN_Y_Z_WAS_GENERATED,
                testDataBatch.getStartValue(), testDataBatch.getNumberStartAlarms(),
                testDataBatch.getDataPointNotifierType().getName());

        assertEquals(msg, testDataBatch.getNumberStartAlarms(), alarmResponses.size());
    }

    @After
    public void clean() {
        watchListObjectsCreator.deleteObjects();
        dataPointObjectsCreator.deleteObjects();
    }

    @AfterClass
    public static void cleanAll() {
        dataSourcePointObjectsCreator.deleteObjects();
    }


    @Test
    public void test_when_set_sequence_then_x_alarms_size() {

        //when:
        watchListPage.setSequenceInts(dataSourcePointIdentifier, testDataBatch.getSequencePointValue());

        //and when:
        List<AlarmResponse> alarmResponses = getAlarms(testDataBatch.getDataPointIdentifier(), paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), alarmResponses.size());

        //and then:
        msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_ACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberActiveAlarms());
        assertEquals(msg, testDataBatch.getNumberActiveAlarms(), getNumberActiveAlarmsFromResponse(alarmResponses));

        //and then:
        msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_INACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberInactiveAlarms());
        assertEquals(msg, testDataBatch.getNumberInactiveAlarms(), getNumberInactiveAlarmsFromResponse(alarmResponses));

    }
}
