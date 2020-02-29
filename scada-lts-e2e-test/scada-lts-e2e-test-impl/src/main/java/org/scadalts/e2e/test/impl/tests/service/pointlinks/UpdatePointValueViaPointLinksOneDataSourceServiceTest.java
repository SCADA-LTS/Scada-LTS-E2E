package org.scadalts.e2e.test.impl.tests.service.pointlinks;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.AllObjectsForPointLinkTestsUtil;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.ServiceTestsUtil;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(E2eTestParameterizedRunner.class)
public class UpdatePointValueViaPointLinksOneDataSourceServiceTest {

    @Parameterized.Parameters(name = "{index}: value:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String expectedValue;

    public UpdatePointValueViaPointLinksOneDataSourceServiceTest(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    private static AllObjectsForPointLinkTestsUtil allObjectsForPointLinkTestsUtil;
    private static DataPointCriteria source;
    private static DataPointCriteria target;

    @BeforeClass
    public static void setup() {

        DataSourceIdentifier dataSourceName = IdentifierObjectFactory.dataSourceName();

        DataSourcePointCriteria sourcePointSourceCriteria = DataSourcePointCriteria.criteria(dataSourceName,
                IdentifierObjectFactory.dataPointSourceName());
        DataSourcePointCriteria sourcePointTargetCriteria = DataSourcePointCriteria.criteria(dataSourceName,
                IdentifierObjectFactory.dataPointTargetName());

        source = sourcePointSourceCriteria.getDataPoint();
        target = sourcePointTargetCriteria.getDataPoint();

        PointLinkCriteria criteria = PointLinkCriteria.update(sourcePointSourceCriteria, sourcePointTargetCriteria);
        allObjectsForPointLinkTestsUtil = new AllObjectsForPointLinkTestsUtil(E2eAbstractRunnable.getNavigationPage(),
                criteria);
        allObjectsForPointLinkTestsUtil.createObjects();
    }

    @AfterClass
    public static void clean() {
        allObjectsForPointLinkTestsUtil.deleteObjects();
    }

    @Test
    public void test_point_links() {

        //given:
        Xid sourceXid = source.getXid();
        Xid targetXid = target.getXid();

        PointValueParams pointTarget = new PointValueParams(targetXid.getValue());
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(expectedValue)
                .xid(sourceXid.getValue())
                .build();

        //when:
        ServiceTestsUtil.setValue(cmpParams);

        //and when:
        E2eResponse<PointValueResponse> getResponse = ServiceTestsUtil.getValue(pointTarget, expectedValue);
        PointValueResponse getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);
        assertEquals(targetXid.getValue(), getResult.getXid());
        assertEquals(expectedValue, getResult.getFormattedValue());
    }
}
