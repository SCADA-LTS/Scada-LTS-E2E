package org.scadalts.e2e.test.impl.tests.performance.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestDataBatch;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

@Log4j2
@RunWith(TestWithPageRunner.class)
public class GetLivesAggregationTwoPointsPerformanceTest {

    private static PaginationParams paginationParams = PaginationParams.all();

    private static List<StorungsAndAlarmsObjectsCreator> storungsAndAlarmsObjectsCreators;
    private static List<TestDataBatch> testDataBatchs;

    @BeforeClass
    public static void setup() {
        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        int nWord = 10000;
        testDataBatchs = generateDataTestRandom(nWord, DataPointNotifierType.ALARM, 2);

        storungsAndAlarmsObjectsCreators = new ArrayList<>();
        for(TestDataBatch testDataBatch: testDataBatchs) {
            StorungsAndAlarmsObjectsCreator creator = createDataSourcePointAndGetCreator(testDataBatch, navigationPage);
            storungsAndAlarmsObjectsCreators.add(creator);
        }

        TestDataBatch testDataBatch1 = testDataBatchs.get(0);
        TestDataBatch testDataBatch2 = testDataBatchs.get(1);

        StorungsAndAlarmsObjectsCreator creator1 = storungsAndAlarmsObjectsCreators.get(0);
        StorungsAndAlarmsObjectsCreator creator2 = storungsAndAlarmsObjectsCreators.get(1);

        for(int i = 0; i < nWord; i++) {
            Integer value1 = testDataBatch1.getSequencePointValue().get(i);
            Integer value2 = testDataBatch2.getSequencePointValue().get(i);

            creator1.setDataPointValue(value1);
            creator2.setDataPointValue(value2);
            logger.info("iteration: {}, set values: {}, {}", i, value1, value2);
        }

    }

    @AfterClass
    public static void cleanAll() {
        for(StorungsAndAlarmsObjectsCreator creator: storungsAndAlarmsObjectsCreators) {
            try {
                creator.deleteObjects();
            } catch (Exception ex) {
                logger.warn(ex.getMessage(), ex);
            }
        }
    }

    @Test
    public void test_when_set_sequence_then_one_size_active_lives() {

        for(TestDataBatch testDataBatch: testDataBatchs) {

            logger.info("data point name: {}", testDataBatch.getDataPointIdentifier());

            //when:
            List<StorungAlarmResponse> storungAlarmRespons = getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(),
                    a -> getActiveAlarmsFromResponseNumber(a) == testDataBatch.getActiveAlarmsNumber(),
                    paginationParams);

            //then:
            String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_ACTIVE_DIFFERENT_FROM_Z,
                    testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                    testDataBatch.getActiveAlarmsNumber());
            assertEquals(msg, testDataBatch.getActiveAlarmsNumber(), getActiveAlarmsFromResponseNumber(storungAlarmRespons));
        }
    }

    @Test
    public void test_when_first_point_set_sequence_then_x_size_inactive_lives() {

        //given:
        TestDataBatch testDataBatch = testDataBatchs.get(0);
        logger.info("data point name: {}", testDataBatch.getDataPointIdentifier());

        //when:
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(),
                a -> getInactiveAlarmsFromResponseNumber(a) == testDataBatch.getInactiveAlarmsNumber(),
                paginationParams);

        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_INACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getInactiveAlarmsNumber());
        assertEquals(msg, testDataBatch.getInactiveAlarmsNumber(), getInactiveAlarmsFromResponseNumber(storungAlarmRespons));

    }


    @Test
    public void test_when_second_point_set_sequence_then_x_size_inactive_lives() {

        //given:
        TestDataBatch testDataBatch = testDataBatchs.get(1);
        logger.info("data point name: {}", testDataBatch.getDataPointIdentifier());

        //when:
        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(),
                a -> getInactiveAlarmsFromResponseNumber(a) == testDataBatch.getInactiveAlarmsNumber(),
                paginationParams);

        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_INACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getInactiveAlarmsNumber());
        assertEquals(msg, testDataBatch.getInactiveAlarmsNumber(), getInactiveAlarmsFromResponseNumber(storungAlarmRespons));
    }

    @Test
    public void test_when_set_sequence_then_not_duplicate_lives() {

        for(TestDataBatch testDataBatch: testDataBatchs) {

            logger.info("data point name: {}", testDataBatch.getDataPointIdentifier());

            //when:
            List<StorungAlarmResponse> storungAlarmRespons = getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(), paginationParams);

            //then:
            assertFalse(isDuplicates(storungAlarmRespons));
        }
    }

    private static boolean isDuplicates(List<StorungAlarmResponse> lives) {
        Set<String> result = lives.stream().map(StorungAlarmResponse::getId).collect(Collectors.toSet());
        return lives.size() != result.size();
    }
}
