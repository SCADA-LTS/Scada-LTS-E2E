package org.scadalts.e2e.test.impl.tests.service.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestDataBatch;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

@Log4j2
@RunWith(TestWithPageRunner.class)
public class GetLivesAggregationPerformanceValuesServiceTest {

    private static PaginationParams paginationParams = PaginationParams.builder()
            .limit(9999)
            .offset(0)
            .build();

    private static StorungsAndAlarmsObjectsCreator storungsAndAlarmsObjectsCreator;
    private static TestDataBatch testDataBatch;

    @BeforeClass
    public static void setup() {
        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        DataSourceCriteria dataSource = DataSourceCriteria.virtualDataSourceSecond();
        testDataBatch = generateDataTestZeroToOnes(100, DataPointNotifierType.ALARM, 1).get(0);

        DataPointCriteria dataPoint = DataPointCriteria.noChangeAllDataLogging(testDataBatch.getDataPointIdentifier(),
                String.valueOf(testDataBatch.getStartValue()));

        storungsAndAlarmsObjectsCreator = new StorungsAndAlarmsObjectsCreator(navigationPage, dataSource, dataPoint);
        storungsAndAlarmsObjectsCreator.createObjects();

        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(), paginationParams);
        String msg = MessageFormat.format(AFTER_INITIALIZING_POINT_VALUE_WITH_X_THEN_Y_Z_WAS_GENERATED,
                testDataBatch.getStartValue(), testDataBatch.getNumberStartAlarms(),
                testDataBatch.getDataPointNotifierType().getName());

        assertEquals(msg, testDataBatch.getNumberStartAlarms(), storungAlarmRespons.size());

        storungsAndAlarmsObjectsCreator.setDataPointValues(testDataBatch.getSequencePointValue());

    }

    @AfterClass
    public static void cleanAll() {
        storungsAndAlarmsObjectsCreator.deleteObjects();
    }

    @Test
    public void test_when_set_sequence_then_one_size_active_lives() {

        //when:
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(), paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_ACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberActiveAlarms());
        assertEquals(msg, testDataBatch.getNumberActiveAlarms(), getNumberActiveAlarmsFromResponse(storungAlarmRespons));
    }

    @Test
    public void test_when_set_sequence_then_x_size_inactive_lives() {

        //when:
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(), paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_INACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberInactiveAlarms());
        assertEquals(msg, testDataBatch.getNumberInactiveAlarms(), getNumberInactiveAlarmsFromResponse(storungAlarmRespons));

    }

    @Test
    public void test_when_set_sequence_then_not_duplicate_lives() {

        //when:
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(), paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_INACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getNumberInactiveAlarms());
        assertFalse(isDuplicates(storungAlarmRespons));
    }

    private static boolean isDuplicates(List<StorungAlarmResponse> lives) {
        Set<String> result = lives.stream().map(StorungAlarmResponse::getId).collect(Collectors.toSet());
        return lives.size() != result.size();
    }
}
