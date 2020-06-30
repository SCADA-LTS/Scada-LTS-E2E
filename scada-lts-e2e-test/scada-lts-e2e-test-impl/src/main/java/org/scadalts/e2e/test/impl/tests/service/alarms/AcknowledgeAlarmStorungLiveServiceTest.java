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
import org.scadalts.e2e.test.impl.utils.TestDataBatch;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.common.utils.VariationUnit;

import java.text.MessageFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.*;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.*;
import static org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil.acknowledgeAlarm;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class AcknowledgeAlarmStorungLiveServiceTest {

    @Parameterized.Parameters(name = "{index}: data point notifier type: {0}, start value: {1}")
    public static Object[][] data() {
        return new Object[][] {
                {DataPointNotifierType.ALARM, "0"},
                {DataPointNotifierType.STORUNG, "0"},
                {DataPointNotifierType.ALARM, "1"},
                {DataPointNotifierType.STORUNG, "1"}

        };
    }

    private final DataPointNotifierType dataPointNotifierType;
    private final String startValue;

    public AcknowledgeAlarmStorungLiveServiceTest(DataPointNotifierType dataPointNotifierType, String startValue) {
        this.dataPointNotifierType = dataPointNotifierType;
        this.startValue = startValue;
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
        DataPointCriteria point = DataPointCriteria.noChange(uniqueIdentifier, startValue);

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
    public void test_invoke_acknowledge_no_change_state_live() {

        //given:
        VariationUnit<Integer> variationUnit = VariationUnit.<Integer>builder()
                .startValue(Integer.valueOf(startValue))
                .build();
        TestDataBatch testDataBatch = TestDataBatch.builder()
                .dataPointNotifierType(dataPointNotifierType)
                .variationUnit(variationUnit)
                .build();

        //when:
        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), alarmResponses.size());

        //and when:
        for (AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        }

        //then:
        List<AlarmResponse> alarmResponsesAfterAcknowledge = getAlarms(uniqueIdentifier, paginationParams);
        assertEquals(EXPECTED_THAT_INVOKE_ACKNOWLEDGE_NO_CHANGE_STATE_LIVE, alarmResponses, alarmResponsesAfterAcknowledge);

    }


    @Test
    public void test_acknowledge_for_inactive_alarm_after_x10_then_removed_from_live() {

        //given:
        VariationUnit<Integer> variationUnit = VariationUnit.<Integer>builder()
                .startValue(Integer.valueOf(startValue))
                .sequence(1)
                .sequence(0)
                .build();
        TestDataBatch testDataBatch = TestDataBatch.builder()
                .dataPointNotifierType(dataPointNotifierType)
                .variationUnit(variationUnit)
                .build();

        //when:
        watchListPage.setSequenceInts(dataSourcePointIdentifier, variationUnit);
        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), alarmResponses.size());

        //and when:
        for(AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED,200, getResponse.getStatus());
        }
        alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        assertEquals(ALARM_INACTIVE_NOT_REMOVED_FROM_LIVE, 0, alarmResponses.size());
    }

    @Test
    public void test_acknowledge_for_inactive_and_active_alarm_then_not_removed_active_from_live() {

        //given:
        VariationUnit<Integer> variationUnit = VariationUnit.<Integer>builder()
                .startValue(Integer.valueOf(startValue))
                .sequence(1)
                .sequence(0)
                .sequence(1)
                .build();
        TestDataBatch testDataBatch = TestDataBatch.builder()
                .dataPointNotifierType(dataPointNotifierType)
                .variationUnit(variationUnit)
                .build();

        //when:
        watchListPage.setSequenceInts(dataSourcePointIdentifier, variationUnit);
        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), alarmResponses.size());

        //and then:
        AlarmResponse activeAlarm = alarmResponses.get(0);
        assertThat(EXPECTED_ACTIVE_ALARM, activeAlarm.getInactivationTime(), anyOf(is(""), is(" ")));

        //and when:
        for(AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        }

        alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        assertTrue(ALARM_ACTIVE_REMOVED_FROM_LIVE, alarmResponses.contains(activeAlarm));

    }

    @Test
    public void test_acknowledge_for_inactive_and_active_alarm_then_removed_inactive_from_live() {

        //given:
        VariationUnit<Integer> variationUnit = VariationUnit.<Integer>builder()
                .startValue(Integer.valueOf(startValue))
                .sequence(1)
                .sequence(0)
                .sequence(1)
                .build();
        TestDataBatch testDataBatch = TestDataBatch.builder()
                .dataPointNotifierType(dataPointNotifierType)
                .variationUnit(variationUnit)
                .build();

        //when:
        watchListPage.setSequenceInts(dataSourcePointIdentifier, variationUnit);
        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), alarmResponses.size());

        //and then:
        AlarmResponse inactiveAlarm = alarmResponses.get(1);
        assertThat(EXPECTED_INACTIVE_ALARM, inactiveAlarm.getInactivationTime(), matchesPattern(DateValidation.DATE_ISO_REGEX));

        //and when:
        for(AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        }
        alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        assertFalse(ALARM_INACTIVE_NOT_REMOVED_FROM_LIVE, alarmResponses.contains(inactiveAlarm));
    }


    @Test
    public void test_acknowledge_for_active_alarm_then_not_removed_from_live() {

        //given:
        VariationUnit<Integer> variationUnit = VariationUnit.<Integer>builder()
                .startValue(Integer.valueOf(startValue))
                .sequence(1)
                .build();
        TestDataBatch testDataBatch = TestDataBatch.builder()
                .dataPointNotifierType(dataPointNotifierType)
                .variationUnit(variationUnit)
                .build();

        //when:
        watchListPage.setSequenceInts(dataSourcePointIdentifier, variationUnit);
        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), alarmResponses.size());

        //and then:
        AlarmResponse activeAlarm = alarmResponses.get(0);
        assertThat(EXPECTED_ACTIVE_ALARM, activeAlarm.getInactivationTime(), anyOf(is(""), is(" ")));

        //and when:
        for(AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        }
        alarmResponses = getAlarms(uniqueIdentifier, paginationParams);

        //then:
        assertTrue(ALARM_ACTIVE_REMOVED_FROM_LIVE, alarmResponses.contains(activeAlarm));
    }
}
