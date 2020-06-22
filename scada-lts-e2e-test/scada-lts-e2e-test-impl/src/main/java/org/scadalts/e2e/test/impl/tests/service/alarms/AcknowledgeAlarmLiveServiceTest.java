package org.scadalts.e2e.test.impl.tests.service.alarms;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.DateValidation;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.List;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.*;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.*;
import static org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil.acknowledgeAlarm;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class AcknowledgeAlarmLiveServiceTest {

    @Parameterized.Parameters(name = "{index}: data point notifier type: {0}")
    public static DataPointNotifierType[] data() {
        return new DataPointNotifierType[] {
                DataPointNotifierType.ALARM,
                DataPointNotifierType.STORUNG,
        };
    }

    private final DataPointNotifierType dataPointNotifierType;

    public AcknowledgeAlarmLiveServiceTest(DataPointNotifierType dataPointNotifierType) {

        this.dataPointNotifierType = dataPointNotifierType;
    }

    private PaginationParams paginationParams = PaginationParams.builder()
            .limit(9999)
            .offset(0)
            .build();

    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private WatchListObjectsCreator watchListObjectsCreator;
    private WatchListPage watchListPage;

    private DataSourcePointIdentifier dataSourcePointIdentifier;
    private DataPointIdentifier uniqueIdentifier;

    @Before
    public void setup() {
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();

        uniqueIdentifier = IdentifierObjectFactory.dataPointNotifierBinaryTypeName(dataPointNotifierType);
        DataPointCriteria point = DataPointCriteria.noChange(uniqueIdentifier, "0");

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, point);
        dataSourcePointObjectsCreator.createObjects();

        dataSourcePointIdentifier = new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(),
                uniqueIdentifier);

        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, WatchListCriteria.criteria(dataSourcePointIdentifier));
        watchListObjectsCreator.createObjects();

        watchListPage = navigationPage.openWatchList();

    }

    @After
    public void clean() {
        watchListObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }


    @Test
    public void test_acknowledge_for_inactive_alarm_then_removed_from_live() {

        //when:
        watchListPage.setValue(dataSourcePointIdentifier, "1")
                .setValue(dataSourcePointIdentifier, "0");
        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_THE_NUMBER_OF_ALARMS_LIVE_DIFFERENT_FROM_Y, "0 -> 1 -> 0", "one");
        assertEquals(msg,1, alarmResponses.size());

        //and when:
        for(AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(AN_ATTEMPT_TO_CALL_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED,200, getResponse.getStatus());
        }

        //then:
        alarmResponses = getAlarms(uniqueIdentifier, paginationParams);
        assertEquals(ALARM_INACTIVE_NOT_REMOVED_FROM_LIVE,0, alarmResponses.size());

    }

    @Test
    public void test_acknowledge_for_inactive_and_active_alarm_then_not_removed_active_from_live() {

        //when:
        watchListPage.setValue(dataSourcePointIdentifier, "1")
                .setValue(dataSourcePointIdentifier, "0")
                .setValue(dataSourcePointIdentifier, "1");

        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_THE_NUMBER_OF_ALARMS_LIVE_DIFFERENT_FROM_Y, "0 -> 1 -> 0 -> 1", "two");
        assertEquals(msg, 2, alarmResponses.size());

        //and when:
        AlarmResponse activeAlarm = alarmResponses.get(0);

        //then:
        assertEquals(EXPECTED_ACTIVE_ALARM, "", activeAlarm.getInactivationTime());

        //and when:
        for(AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(AN_ATTEMPT_TO_CALL_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        }

        alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        assertFalse(ALARM_ACTIVE_REMOVED_FROM_LIVE, alarmResponses.contains(activeAlarm));

    }

    @Test
    public void test_acknowledge_for_inactive_and_active_alarm_then_removed_inactive_from_live() {

        //when:
        watchListPage.setValue(dataSourcePointIdentifier, "1")
                .setValue(dataSourcePointIdentifier, "0")
                .setValue(dataSourcePointIdentifier, "1");

        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_THE_NUMBER_OF_ALARMS_LIVE_DIFFERENT_FROM_Y, "0 -> 1 -> 0 -> 1", "two");
        assertEquals(msg, 2, alarmResponses.size());

        //and when:
        AlarmResponse inactiveAlarm = alarmResponses.get(1);

        //then:
        assertThat(EXPECTED_INACTIVE_ALARM, inactiveAlarm.getInactivationTime(), matchesPattern(DateValidation.DATE_ISO_REGEX));

        //and when:
        for(AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(AN_ATTEMPT_TO_CALL_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        }
        alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        assertTrue(ALARM_INACTIVE_NOT_REMOVED_FROM_LIVE, alarmResponses.contains(inactiveAlarm));
    }


    @Test
    public void test_acknowledge_for_active_alarm_then_not_removed_from_live() {

        //when:
        watchListPage.setValue(dataSourcePointIdentifier, "1");
        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_THE_NUMBER_OF_ALARMS_LIVE_DIFFERENT_FROM_Y, "0 -> 1", "one");
        assertEquals(msg, 1, alarmResponses.size());

        //and when:
        for(AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(AN_ATTEMPT_TO_CALL_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        }

        //then:
        alarmResponses = getAlarms(uniqueIdentifier, paginationParams);
        assertEquals(CALLING_ACKNOWLEDGE_ON_AN_ACTIVE_ALARM_DELETE_IT_FROM_LIVE,1, alarmResponses.size());
    }
}
