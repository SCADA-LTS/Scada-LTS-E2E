package org.scadalts.e2e.test.impl.tests.check.eventdetectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class EventDetectorCheckTest {

    private static final String PROBLEM_WITH_REST_API = "Problem with the test E2E tool.";

    @Parameterized.Parameters(name = "test number: {index}, set value datapoint-to-change: {0}, expected value datapoint-to-read: {0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String expectedValue;

    public EventDetectorCheckTest(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Before
    public void config() {
        TestWithoutPageUtil.preparingTest();
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
        assertEquals("Configuration correction may be required, check if there is a datapoint [xid]: " + TestImplConfiguration.dataPointToChangeXid,200, setResponse.getStatus());
        assertNotNull(PROBLEM_WITH_REST_API, setResult);
        assertThat("Error: ", setResult.getError(), anyOf(equalTo(""), nullValue()));
        assertEquals(PROBLEM_WITH_REST_API, TestImplConfiguration.dataPointToChangeXid, setResult.getDataPointXid());
        assertEquals(expectedValue, setResult.getValue());

        //and when:
        E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getDataPointValue(pointValueParams,
                expectedValue, TestImplConfiguration.waitingAfterSetPointValueMs);
        PointValueResponse getResult = getResponse.getValue();

        //then:
        assertEquals("Configuration correction may be required, check if there is a datapoint [xid]: " + TestImplConfiguration.dataPointToReadXid,200, getResponse.getStatus());
        assertNotNull(PROBLEM_WITH_REST_API, getResult);
        assertEquals(PROBLEM_WITH_REST_API, TestImplConfiguration.dataPointToReadXid, getResult.getXid());
        assertEquals("The script associated with the eventdetector failed. The expected value was not obtained after " + TestImplConfiguration.waitingAfterSetPointValueMs + " [ms]. Event detector from datapoint [xid]: " + TestImplConfiguration.dataPointToChangeXid
                + ". The datapoint being read [xid]: " + TestImplConfiguration.dataPointToReadXid + ".", expectedValue, getResult.getFormattedValue());
    }
}
