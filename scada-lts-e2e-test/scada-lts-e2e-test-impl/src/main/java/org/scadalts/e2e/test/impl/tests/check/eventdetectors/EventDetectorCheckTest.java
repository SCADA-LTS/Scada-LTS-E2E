package org.scadalts.e2e.test.impl.tests.check.eventdetectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithoutPageRunner;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(TestParameterizedWithoutPageRunner.class)
public class EventDetectorCheckTest {

    @Parameterized.Parameters(name = "test number: {index}, set value datapoint-to-change: {0}, expected value datapoint-to-read: {0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String expectedValue;

    public EventDetectorCheckTest(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Test
    public void test_check_detector() {

        //given:
        PointValueParams pointValueParams = new PointValueParams(TestImplConfiguration.dataPointToReadXid);
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(expectedValue)
                .dataPointXid(TestImplConfiguration.dataPointToChangeXid)
                .build();

        //when:
        E2eResponse<CmpParams> setResponse = TestWithoutPageUtil.setDataPointValue(cmpParams);
        CmpParams setResult = setResponse.getValue();

        //then:
        assertEquals(200, setResponse.getStatus());
        assertNotNull(setResult);
        assertEquals("", setResult.getError());
        assertEquals(TestImplConfiguration.dataPointToChangeXid, setResult.getDataPointXid());
        assertEquals(expectedValue, setResult.getValue());

        //and when:
        E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getDataPointValue(pointValueParams,
                expectedValue, TestImplConfiguration.waitingAfterSetPointValueMs);
        PointValueResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);
        assertEquals(TestImplConfiguration.dataPointToReadXid, getResult.getXid());
        assertEquals(expectedValue, getResult.getFormattedValue());
    }
}
