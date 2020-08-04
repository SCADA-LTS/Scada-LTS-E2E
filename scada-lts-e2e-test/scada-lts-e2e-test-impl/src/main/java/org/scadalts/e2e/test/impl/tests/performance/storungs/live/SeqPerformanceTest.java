package org.scadalts.e2e.test.impl.tests.performance.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil;
import org.scadalts.e2e.test.impl.utils.TestDataBatch;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_ACTIVE_DIFFERENT_FROM_Z;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_INACTIVE_DIFFERENT_FROM_Z;

@Log4j2
public class SeqPerformanceTest {

    private static TestDataBatch testDataBatch;
    private static TestDataBatch testDataBatchByTs;

    @BeforeClass
    public static void setup() {
        Path path = Paths.get("C:\\dev\\check-stroungs\\seq.txt");
        testDataBatch = StorungsAndAlarmsUtil.generateDataTestFromFile(path, DataPointNotifierType.ALARM);
        Path pathByTs = Paths.get("C:\\dev\\check-stroungs\\seqByTs.txt");
        testDataBatchByTs = StorungsAndAlarmsUtil.generateDataTestFromFile(pathByTs, DataPointNotifierType.ALARM);

    }

    @Test
    public void test_when_set_sequence_then_one_size_active_lives() {

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_ACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getActiveAlarmsNumber());
        assertEquals(msg, 1, testDataBatch.getActiveAlarmsNumber());
    }

    @Test
    public void test_when_set_sequence_by_ts_then_one_size_active_lives() {

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_ACTIVE_DIFFERENT_FROM_Z,
                testDataBatchByTs.getSequencePointValueWithStart(), testDataBatchByTs.getDataPointNotifierType().getName(),
                testDataBatchByTs.getActiveAlarmsNumber());
        assertEquals(msg, 1, testDataBatchByTs.getActiveAlarmsNumber());
    }

    @Test
    public void test_when_set_sequence_then_x_size_inactive_lives() {

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_INACTIVE_DIFFERENT_FROM_Z,
                testDataBatch.getSequencePointValueWithStart(), testDataBatch.getDataPointNotifierType().getName(),
                testDataBatch.getInactiveAlarmsNumber());
        assertEquals(msg, 2498, testDataBatch.getInactiveAlarmsNumber());

    }

    @Test
    public void test_when_set_sequence_by_ts_then_x_size_inactive_lives() {

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_INACTIVE_DIFFERENT_FROM_Z,
                testDataBatchByTs.getSequencePointValueWithStart(), testDataBatchByTs.getDataPointNotifierType().getName(),
                testDataBatchByTs.getInactiveAlarmsNumber());
        assertEquals(msg, 2497, testDataBatchByTs.getInactiveAlarmsNumber());

    }
}
