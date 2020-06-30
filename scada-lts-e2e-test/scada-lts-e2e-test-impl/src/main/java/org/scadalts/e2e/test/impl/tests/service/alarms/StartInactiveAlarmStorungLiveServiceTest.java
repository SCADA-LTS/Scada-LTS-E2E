package org.scadalts.e2e.test.impl.tests.service.alarms;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.getAlarms;

@Log4j2
@RunWith(TestWithPageRunner.class)
public class StartInactiveAlarmStorungLiveServiceTest {

    private static DataPointIdentifier alarmIdentifier;
    private static DataPointIdentifier stroungIdentifier;
    private static PaginationParams paginationParams = PaginationParams.builder()
            .limit(9999)
            .offset(0)
            .build();

    private static DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private static WatchListObjectsCreator watchListObjectsCreator;

    @BeforeClass
    public static void setup() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();

        alarmIdentifier = IdentifierObjectFactory.dataPointAlarmBinaryTypeName();
        stroungIdentifier = IdentifierObjectFactory.dataPointStorungBinaryTypeName();


        DataPointCriteria pointAlarm = DataPointCriteria.noChange(alarmIdentifier, "0");
        DataPointCriteria pointStorung = DataPointCriteria.noChange(stroungIdentifier, "0");

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, pointAlarm, pointStorung);
        dataSourcePointObjectsCreator.createObjects();

        DataSourcePointIdentifier dataSourcePointAlarmIdentifier = new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(),
                alarmIdentifier);
        DataSourcePointIdentifier dataSourcePointStorungIdentifier = new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(),
                stroungIdentifier);

        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage,
                WatchListCriteria.criteria(dataSourcePointAlarmIdentifier, dataSourcePointStorungIdentifier));
        watchListObjectsCreator.createObjects();
    }

    @AfterClass
    public static void clean() {
        watchListObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_start_value_zero_for_alarm_then_not_raise_alarm() {
        
        //when:
        List<AlarmResponse> alarmResponse = getAlarms(alarmIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z, "0", "alarm", "0");
        assertEquals(msg,0, alarmResponse.size());
    }

    @Test
    public void test_start_value_zero_for_storung_then_not_raise_storung() {

        //when:
        List<AlarmResponse> alarmResponse = getAlarms(stroungIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z, "0", "storung", "0");
        assertEquals(msg,0, alarmResponse.size());
    }
}