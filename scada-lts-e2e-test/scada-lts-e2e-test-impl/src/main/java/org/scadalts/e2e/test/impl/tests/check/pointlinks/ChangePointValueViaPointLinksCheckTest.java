package org.scadalts.e2e.test.impl.tests.check.pointlinks;

import org.junit.Ignore;
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

@Ignore
@RunWith(TestParameterizedWithoutPageRunner.class)
public class ChangePointValueViaPointLinksCheckTest {

    @Parameterized.Parameters(name = "{index}: value:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String expectedValue;

    public ChangePointValueViaPointLinksCheckTest(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Test
    public void test_check_point_link() {

        //given:
        PointValueParams pointValueParams = new PointValueParams(TestImplConfiguration.dataPointTargetXid);
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(expectedValue)
                .xid(TestImplConfiguration.dataPointSourceXid)
                .build();

        //when:
        E2eResponse<CmpParams> setResponse = TestWithoutPageUtil.setValue(cmpParams);
        CmpParams setResult = setResponse.getValue();

        //then:
        assertEquals(200, setResponse.getStatus());
        assertNotNull(setResult);
        assertEquals("", setResult.getError());
        assertEquals(TestImplConfiguration.dataPointSourceXid, setResult.getXid());
        assertEquals(expectedValue, setResult.getValue());

        //and when:
        E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getValue(pointValueParams, expectedValue);
        PointValueResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);
        assertEquals(TestImplConfiguration.dataPointTargetXid, getResult.getXid());
        assertEquals(expectedValue, getResult.getFormattedValue());
    }
}
