package org.scadalts.e2e.test.impl.tests.check.pointlinks;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.core.criterias.Script;
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

@Log4j2
@RunWith(TestParameterizedWithoutPageRunner.class)
public class ChangePointValueViaPointLinksCheckTest {

    @Parameterized.Parameters(name = "test number: {index}, set value datapoint-source: {0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String value;
    private final String expectedValue;

    public ChangePointValueViaPointLinksCheckTest(String value) {
        this.value = value;
        this.expectedValue = Script.sourceValueIncreasedOne().executeInJava(value);
    }

    @Test
    public void test_check_point_link() {

        //given:
        logger.info("set value datapoint-source: {}, expected value datapoint-target: {}", value, expectedValue);

        PointValueParams pointValueParams = new PointValueParams(TestImplConfiguration.dataPointTargetXid);
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(value)
                .dataPointXid(TestImplConfiguration.dataPointSourceXid)
                .build();

        //when:
        E2eResponse<CmpParams> setResponse = TestWithoutPageUtil.setDataPointValue(cmpParams);
        CmpParams setResult = setResponse.getValue();

        //then:
        assertEquals(200, setResponse.getStatus());
        assertNotNull(setResult);
        assertEquals("", setResult.getError());
        assertEquals(TestImplConfiguration.dataPointSourceXid, setResult.getDataPointXid());
        assertEquals(value, setResult.getValue());

        //and when:
        E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getDataPointValue(pointValueParams, expectedValue,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        PointValueResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);
        assertEquals(TestImplConfiguration.dataPointTargetXid, getResult.getXid());
        assertEquals(expectedValue, getResult.getFormattedValue());
    }
}
