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
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.getAlarms;

@Log4j2
@RunWith(TestWithPageRunner.class)
public class GetInactiveAlarmLiveServiceTest {

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

        alarmIdentifier = IdentifierObjectFactory.dataPointAlarmName(DataPointType.BINARY);
        stroungIdentifier = IdentifierObjectFactory.dataPointStorungName(DataPointType.BINARY);


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

        //Simulate the change of value on the points 0 -> 1 -> 0
        navigationPage.openWatchList()
                .setValue(dataSourcePointAlarmIdentifier, "1")
                .setValue(dataSourcePointAlarmIdentifier, "0")
                .setValue(dataSourcePointStorungIdentifier, "1")
                .setValue(dataSourcePointStorungIdentifier, "0");

    }

    @AfterClass
    public static void clean() {
        watchListObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }

    //After aggregation alarms
    @Test
    public void test_response_size_then_2_for_alarm() {

        //given:
        List<AlarmResponse> alarmResponse = getAlarms(alarmIdentifier, paginationParams);

        //then:
        assertEquals(2, alarmResponse.size());
    }

    @Test
    public void test_response_activation_time_then_not_empty_for_alarm() {

        //given:
        List<AlarmResponse> alarmResponse = getAlarms(alarmIdentifier, paginationParams);

        //then:
        assertNotEquals("", alarmResponse.get(0).getActivationTime());
    }

    @Test
    public void test_response_inactivation_time_then_not_empty_for_alarm() {

        //given:
        List<AlarmResponse> alarmResponse = getAlarms(alarmIdentifier, paginationParams);

        //then:
        assertNotEquals("", alarmResponse.get(0).getInactivationTime());
    }

    @Test
    public void test_response_name_then_point_name_for_alarm() {

        //given:
        List<AlarmResponse> alarmResponse = getAlarms(alarmIdentifier, paginationParams);

        //then:
        assertEquals(alarmIdentifier.getValue(), alarmResponse.get(0).getName());
    }

    @Test
    public void test_response_level_then_AlarmLevel_for_alarm() {

        //given:
        List<AlarmResponse> alarmResponse = getAlarms(alarmIdentifier, paginationParams);

        //then:
        assertEquals(AlarmLevel.INFORMATION.getId(), alarmResponse.get(0).getLevel());
    }

    //After aggregation storungs
    @Test
    public void test_response_size_then_2_for_storung() {

        //given:
        List<AlarmResponse> alarmResponse = getAlarms(stroungIdentifier, paginationParams);

        //then:
        assertEquals(2, alarmResponse.size());
    }

    @Test
    public void test_response_activation_time_then_not_empty_for_storung() {

        //given:
        List<AlarmResponse> alarmResponse = getAlarms(stroungIdentifier, paginationParams);

        //then:
        assertNotEquals("", alarmResponse.get(0).getActivationTime());
    }

    @Test
    public void test_response_inactivation_time_then_not_empty_for_storung() {

        //given:
        List<AlarmResponse> alarmResponse = getAlarms(stroungIdentifier, paginationParams);
logger.info("res: {}", alarmResponse);
        //then:
        assertNotEquals("", alarmResponse.get(0).getInactivationTime());
    }

    @Test
    public void test_response_name_then_point_name_for_storung() {

        //given:
        List<AlarmResponse> alarmResponse = getAlarms(stroungIdentifier, paginationParams);

        //then:
        assertEquals(stroungIdentifier.getValue(), alarmResponse.get(0).getName());
    }

    @Test
    public void test_response_level_then_AlarmLevel_for_storung() {

        //given:
        List<AlarmResponse> alarmResponse = getAlarms(stroungIdentifier, paginationParams);

        //then:
        assertEquals(AlarmLevel.URGENT.getId(), alarmResponse.get(0).getLevel());
    }
}