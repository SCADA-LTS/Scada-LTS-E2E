package org.scadalts.e2e.test.impl.tests.service.set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.DataSourcePointTestObjectsUtil;
import org.scadalts.e2e.test.impl.utils.ServiceTestsUtil;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(E2eTestParameterizedRunner.class)
public class CmpServiceTest {

    @Parameterized.Parameters(name = "{index}: value:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String expectedValue;

    public CmpServiceTest(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    private static DataSourcePointTestObjectsUtil allObjectsForPointLinkTestsUtil;
    private static DataPointCriteria source;

    @BeforeClass
    public static void setup() {
        source = DataPointCriteria.numericNoChange(1234);

        allObjectsForPointLinkTestsUtil = new DataSourcePointTestObjectsUtil(E2eAbstractRunnable.getNavigationPage(),
                source);
        allObjectsForPointLinkTestsUtil.createObjects();
    }

    @AfterClass
    public static void clean() {
        allObjectsForPointLinkTestsUtil.deleteObjects();
    }

    @Test
    public void test_set_method_service_then_status_http_200() {

        //given:
        Xid sourceXid = source.getXid();
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(expectedValue)
                .xid(sourceXid.getValue())
                .build();

        //when:
        E2eResponse<CmpParams> setResponse = ServiceTestsUtil.setValue(cmpParams);

        //then:
        assertEquals(200, setResponse.getStatus());
    }

    @Test
    public void test_set_method_service_then_no_error() {

        //given:
        Xid sourceXid = source.getXid();
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(expectedValue)
                .xid(sourceXid.getValue())
                .build();

        //when:
        E2eResponse<CmpParams> setResponse = ServiceTestsUtil.setValue(cmpParams);
        CmpParams setResult = setResponse.getValue();

        //then:
        assertNotNull(setResult);
        assertEquals("", setResult.getError());
    }

    @Test
    public void test_set_method_service_then_xid() {

        //given:
        Xid sourceXid = source.getXid();
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(expectedValue)
                .xid(sourceXid.getValue())
                .build();

        //when:
        E2eResponse<CmpParams> setResponse = ServiceTestsUtil.setValue(cmpParams);
        CmpParams setResult = setResponse.getValue();

        //then:
        assertNotNull(setResult);
        assertEquals(sourceXid.getValue(), setResult.getXid());
    }

    @Test
    public void test_set_method_service_then_value() {

        //given:
        Xid sourceXid = source.getXid();
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(expectedValue)
                .xid(sourceXid.getValue())
                .build();

        //when:
        E2eResponse<CmpParams> setResponse = ServiceTestsUtil.setValue(cmpParams);
        CmpParams setResult = setResponse.getValue();

        //then:
        assertNotNull(setResult);
        assertEquals(expectedValue, setResult.getValue());
    }
}
