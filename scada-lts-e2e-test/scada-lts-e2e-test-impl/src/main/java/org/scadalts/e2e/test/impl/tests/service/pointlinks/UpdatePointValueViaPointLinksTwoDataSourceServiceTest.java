package org.scadalts.e2e.test.impl.tests.service.pointlinks;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.core.criterias.identifiers.Xid;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.impl.creators.AllObjectsForPointLinkTestCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(TestParameterizedWithPageRunner.class)
public class UpdatePointValueViaPointLinksTwoDataSourceServiceTest {

    @Parameterized.Parameters(name = "{index}: value:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String expectedValue;

    public UpdatePointValueViaPointLinksTwoDataSourceServiceTest(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    private static AllObjectsForPointLinkTestCreator allObjectsForPointLinkTestCreator;
    private static DataPointCriteria dataPointSource;
    private static DataPointCriteria dataPointTarget;

    @BeforeClass
    public static void setup() {

        DataSourcePointCriteria source = DataSourcePointCriteria.criteria(IdentifierObjectFactory.dataSourceSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                IdentifierObjectFactory.dataPointSourceName(DataPointType.NUMERIC));
        DataSourcePointCriteria target = DataSourcePointCriteria.criteria(IdentifierObjectFactory.dataSourceTargetName(DataSourceType.VIRTUAL_DATA_SOURCE),
                IdentifierObjectFactory.dataPointTargetName(DataPointType.NUMERIC));

        PointLinkCriteria criteria = PointLinkCriteria.change(source, target);

        dataPointSource = source.getDataPoint();
        dataPointTarget = target.getDataPoint();
        allObjectsForPointLinkTestCreator = new AllObjectsForPointLinkTestCreator(TestWithPageUtil.getNavigationPage(),
                criteria);
        allObjectsForPointLinkTestCreator.createObjects();
    }

    @AfterClass
    public static void clean() {
        allObjectsForPointLinkTestCreator.deleteObjects();
    }

    @Test
    public void test_point_links() {

        //given:
        Xid sourceXid = dataPointSource.getXid();
        Xid targetXid = dataPointTarget.getXid();

        PointValueParams pointValueParams = new PointValueParams(targetXid.getValue());
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(expectedValue)
                .xid(sourceXid.getValue())
                .build();

        //when:
        TestWithoutPageUtil.setValue(cmpParams);

        //and when:
        E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getValue(pointValueParams, expectedValue);
        PointValueResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);
        assertEquals(targetXid.getValue(), getResult.getXid());
        assertEquals(expectedValue, getResult.getFormattedValue());
    }
}
