package org.scadalts.e2e.test.impl.tests.service.cmp;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(TestParameterizedWithPageRunner.class)
public class CmpServiceTest {

    @Parameterized.Parameters(name = "{index}: expected:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String expectedValue;

    public CmpServiceTest(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    private static DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private static DataPointCriteria source;

    @BeforeClass
    public static void setup() {
        source = DataPointCriteria.numericNoChange(1234);

        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(),
                source);
        dataSourcePointObjectsCreator.createObjects();
    }

    @AfterClass
    public static void clean() {
        dataSourcePointObjectsCreator.deleteObjects();
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
        E2eResponse<CmpParams> setResponse = TestWithoutPageUtil.setValue(cmpParams);

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
        E2eResponse<CmpParams> setResponse = TestWithoutPageUtil.setValue(cmpParams);
        CmpParams setResult = setResponse.getValue();

        //then:
        assertNotNull(setResult);
        assertEquals("", setResult.getError());
    }

    @Test
    public void test_set_method_service_then_expect_xid() {

        //given:
        Xid sourceXid = source.getXid();
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(expectedValue)
                .xid(sourceXid.getValue())
                .build();

        //when:
        E2eResponse<CmpParams> setResponse = TestWithoutPageUtil.setValue(cmpParams);
        CmpParams setResult = setResponse.getValue();

        //then:
        assertNotNull(setResult);
        assertEquals(sourceXid.getValue(), setResult.getXid());
    }

    @Test
    public void test_set_method_service_then_expect_value() {

        //given:
        Xid sourceXid = source.getXid();
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(expectedValue)
                .xid(sourceXid.getValue())
                .build();

        //when:
        E2eResponse<CmpParams> setResponse = TestWithoutPageUtil.setValue(cmpParams);
        CmpParams setResult = setResponse.getValue();

        //then:
        assertNotNull(setResult);
        assertEquals(expectedValue, setResult.getValue());
    }
}
