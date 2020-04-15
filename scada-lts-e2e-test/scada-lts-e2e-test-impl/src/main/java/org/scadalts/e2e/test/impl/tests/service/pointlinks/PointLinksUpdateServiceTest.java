package org.scadalts.e2e.test.impl.tests.service.pointlinks;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.creators.AllObjectsForPointLinkTestCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class PointLinksUpdateServiceTest {

    @Parameterized.Parameters(name = "{index}: value:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String value;

    public PointLinksUpdateServiceTest(String value) {
        this.value = value;
    }

    private static AllObjectsForPointLinkTestCreator allObjectsForPointLinkTestCreator;
    private static DataPointCriteria source;
    private static DataPointCriteria target;
    private static Script script;

    @BeforeClass
    public static void before() {
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataSourcePointCriteria sourcePointSourceCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria,
                IdentifierObjectFactory.dataPointSourceName(DataPointType.NUMERIC));
        DataSourcePointCriteria sourcePointTargetCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria,
                IdentifierObjectFactory.dataPointTargetName(DataPointType.NUMERIC));

        source = sourcePointSourceCriteria.getDataPoint();
        target = sourcePointTargetCriteria.getDataPoint();

        script = Script.sourceValueIncreasedOne();
        PointLinkCriteria pointLinkUpdate = PointLinkCriteria.update(sourcePointSourceCriteria, sourcePointTargetCriteria, script);
        allObjectsForPointLinkTestCreator = new AllObjectsForPointLinkTestCreator(TestWithPageUtil.getNavigationPage(),
                pointLinkUpdate);
        allObjectsForPointLinkTestCreator.createObjects();
    }

    @AfterClass
    public static void clean() {
        allObjectsForPointLinkTestCreator.deleteObjects();
    }

    @Test
    public void test_check_point_link() {

        //given:
        Xid pointSource = source.getXid();
        Xid pointTarget = target.getXid();
        String expectedValue = script.executeInJava(value);
        logger.info("value: {}, expected: {}", value, expectedValue);

        PointValueParams pointValueParams = new PointValueParams(pointTarget.getValue());
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(value)
                .xid(pointSource.getValue())
                .build();

        //when:
        E2eResponse<CmpParams> setResponse = TestWithoutPageUtil.setDataPointValue(cmpParams);
        CmpParams setResult = setResponse.getValue();

        //then:
        assertEquals(200, setResponse.getStatus());
        assertNotNull(setResult);
        assertEquals("", setResult.getError());
        assertEquals(pointSource.getValue(), setResult.getXid());
        assertEquals(value, setResult.getValue());

        //and when:
        E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getDataPointValue(pointValueParams, expectedValue,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        PointValueResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);
        assertEquals(pointTarget.getValue(), getResult.getXid());
        assertEquals(expectedValue, getResult.getFormattedValue());
    }
}
