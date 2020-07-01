package org.scadalts.e2e.test.impl.tests.service.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.common.utils.VariationUnit;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.AcknowledgeResponse;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil;
import org.scadalts.e2e.test.impl.utils.DateValidation;
import org.scadalts.e2e.test.impl.utils.TestDataBatch;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.*;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class AcknowledgeLivesServiceTest {

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

    public AcknowledgeLivesServiceTest(DataPointNotifierType dataPointNotifierType, String startValue) {
        this.dataPointNotifierType = dataPointNotifierType;
        this.startValue = startValue;
    }

    private PaginationParams paginationParams = PaginationParams.builder()
            .limit(9999)
            .offset(0)
            .build();

    private StorungsAndAlarmsObjectsCreator storungsAndAlarmsObjectsCreator;
    private DataPointIdentifier uniqueIdentifier;

    @Before
    public void setup() {
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();

        uniqueIdentifier = IdentifierObjectFactory.dataPointNotifierBinaryTypeName(dataPointNotifierType);
        DataPointCriteria point = DataPointCriteria.noChange(uniqueIdentifier, startValue);

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        storungsAndAlarmsObjectsCreator = new StorungsAndAlarmsObjectsCreator(navigationPage, dataSourceCriteria, point);
        storungsAndAlarmsObjectsCreator.createObjects();
    }

    @After
    public void clean() {
        storungsAndAlarmsObjectsCreator.deleteObjects();
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
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsSortByActivationTime(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), storungAlarmRespons.size());

        //and when:
        for (StorungAlarmResponse storungAlarmResponse : storungAlarmRespons) {

            AcknowledgeResponse result = StorungsAndAlarmsUtil.acknowledgeAlarm(storungAlarmResponse.getId());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, "OK", result.getRequestStatus());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_CAUSES_ERROR, "none", result.getError());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_RETURNING_OTHER_ID, storungAlarmResponse.getId(), result.getId());
        }

        //then:
        List<StorungAlarmResponse> storungAlarmResponsesAfterAcknowledge = getAlarmsSortByActivationTime(uniqueIdentifier, paginationParams);
        assertEquals(EXPECTED_THAT_INVOKE_ACKNOWLEDGE_NO_CHANGE_STATE_LIVE, storungAlarmRespons, storungAlarmResponsesAfterAcknowledge);

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
        storungsAndAlarmsObjectsCreator.setDataPointValues(variationUnit);
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsSortByActivationTime(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), storungAlarmRespons.size());

        //and when:
        for(StorungAlarmResponse storungAlarmResponse : storungAlarmRespons) {

            AcknowledgeResponse result = StorungsAndAlarmsUtil.acknowledgeAlarm(storungAlarmResponse.getId());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, "OK", result.getRequestStatus());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_CAUSES_ERROR, "none", result.getError());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_RETURNING_OTHER_ID, storungAlarmResponse.getId(), result.getId());
        }
        storungAlarmRespons = getAlarmsSortByActivationTime(uniqueIdentifier, paginationParams);

        //then:
        assertEquals(ALARM_INACTIVE_NOT_REMOVED_FROM_LIVE, 0, storungAlarmRespons.size());
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
        storungsAndAlarmsObjectsCreator.setDataPointValues(variationUnit);
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsSortByActivationTime(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), storungAlarmRespons.size());

        //and then:
        StorungAlarmResponse activeAlarm = storungAlarmRespons.get(0);
        assertThat(EXPECTED_ACTIVE_ALARM, activeAlarm.getInactivationTime(), anyOf(is(""), is(" ")));

        //and when:
        for(StorungAlarmResponse storungAlarmResponse : storungAlarmRespons) {

            AcknowledgeResponse result = StorungsAndAlarmsUtil.acknowledgeAlarm(storungAlarmResponse.getId());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, "OK", result.getRequestStatus());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_CAUSES_ERROR, "none", result.getError());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_RETURNING_OTHER_ID, storungAlarmResponse.getId(), result.getId());
        }

        storungAlarmRespons = getAlarmsSortByActivationTime(uniqueIdentifier, paginationParams);

        //then:
        assertTrue(ALARM_ACTIVE_REMOVED_FROM_LIVE, storungAlarmRespons.contains(activeAlarm));

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
        storungsAndAlarmsObjectsCreator.setDataPointValues(variationUnit);
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsSortByActivationTime(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), storungAlarmRespons.size());

        //and then:
        StorungAlarmResponse inactiveAlarm = storungAlarmRespons.get(1);
        assertThat(EXPECTED_INACTIVE_ALARM, inactiveAlarm.getInactivationTime(), matchesPattern(DateValidation.DATE_ISO_REGEX));

        //and when:
        for(StorungAlarmResponse storungAlarmResponse : storungAlarmRespons) {

            AcknowledgeResponse result = StorungsAndAlarmsUtil.acknowledgeAlarm(storungAlarmResponse.getId());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, "OK", result.getRequestStatus());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_CAUSES_ERROR, "none", result.getError());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_RETURNING_OTHER_ID, storungAlarmResponse.getId(), result.getId());
        }
        storungAlarmRespons = getAlarmsSortByActivationTime(uniqueIdentifier, paginationParams);

        //then:
        assertFalse(ALARM_INACTIVE_NOT_REMOVED_FROM_LIVE, storungAlarmRespons.contains(inactiveAlarm));
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
        storungsAndAlarmsObjectsCreator.setDataPointValues(variationUnit);
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsSortByActivationTime(uniqueIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberAlarms());
        assertEquals(msg, testDataBatch.getNumberAlarms(), storungAlarmRespons.size());

        //and then:
        StorungAlarmResponse activeAlarm = storungAlarmRespons.get(0);
        assertThat(EXPECTED_ACTIVE_ALARM, activeAlarm.getInactivationTime(), anyOf(is(""), is(" ")));

        //and when:
        for(StorungAlarmResponse storungAlarmResponse : storungAlarmRespons) {

            AcknowledgeResponse result = StorungsAndAlarmsUtil.acknowledgeAlarm(storungAlarmResponse.getId());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_OK, "OK", result.getRequestStatus());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_CAUSES_ERROR, "none", result.getError());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_RETURNING_OTHER_ID, storungAlarmResponse.getId(), result.getId());
        }
        storungAlarmRespons = getAlarmsSortByActivationTime(uniqueIdentifier, paginationParams);

        //then:
        assertTrue(ALARM_ACTIVE_REMOVED_FROM_LIVE, storungAlarmRespons.contains(activeAlarm));
    }
}
