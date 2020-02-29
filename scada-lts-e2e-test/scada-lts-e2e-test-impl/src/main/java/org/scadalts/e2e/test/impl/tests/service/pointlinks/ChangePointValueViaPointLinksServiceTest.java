package org.scadalts.e2e.test.impl.tests.service.pointlinks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.dicts.EventType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;
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
public class ChangePointValueViaPointLinksServiceTest {

    @Parameterized.Parameters(name = "{index}: {0} updatePeriodValue: {1}, {2}")
    public static Object[][] data() { return new Object[][]{
                {UpdatePeriodType.SECOND, 1, EventType.CHANGE},
                {UpdatePeriodType.SECOND, 5, EventType.CHANGE},
                {UpdatePeriodType.SECOND, 10, EventType.CHANGE},
                {UpdatePeriodType.SECOND, 30, EventType.CHANGE},
                {UpdatePeriodType.MILLISECOUND, 100, EventType.CHANGE},
                {UpdatePeriodType.MILLISECOUND, 1000, EventType.CHANGE},
                {UpdatePeriodType.MILLISECOUND, 3000, EventType.CHANGE},
                {UpdatePeriodType.MINUTE, 1, EventType.CHANGE},
                {UpdatePeriodType.MINUTE, 10, EventType.CHANGE},
                {UpdatePeriodType.SECOND, 1, EventType.UPDATE},
                {UpdatePeriodType.SECOND, 5, EventType.UPDATE},
                {UpdatePeriodType.SECOND, 10, EventType.UPDATE},
                {UpdatePeriodType.SECOND, 30, EventType.UPDATE},
                {UpdatePeriodType.MILLISECOUND, 100, EventType.UPDATE},
                {UpdatePeriodType.MILLISECOUND, 1000, EventType.UPDATE},
                {UpdatePeriodType.MILLISECOUND, 3000, EventType.UPDATE},
                {UpdatePeriodType.MINUTE, 1, EventType.UPDATE},
                {UpdatePeriodType.MINUTE, 10, EventType.UPDATE},
        };
    }

    private final UpdatePeriodType updatePeriodType;
    private final int updatePeriodValue;
    private final EventType eventType;

    public ChangePointValueViaPointLinksServiceTest(UpdatePeriodType updatePeriodType, int updatePeriodValue, EventType eventType) {
        this.updatePeriodType = updatePeriodType;
        this.updatePeriodValue = updatePeriodValue;
        this.eventType = eventType;
    }

    private AllObjectsForPointLinkTestsUtil allObjectsForPointLinkTestsUtil;
    private DataPointCriteria source;
    private DataPointCriteria target;
    private final static Collection<String> values = ChangePointValuesProvider.paramsToTests();

    @Before
    public void setup() {

        DataSourceIdentifier dataSourceName = IdentifierObjectFactory.dataSourceName();

        DataSourcePointCriteria sourcePointSourceCriteria = DataSourcePointCriteria.criteria(dataSourceName,
                IdentifierObjectFactory.dataPointSourceName());
        DataSourcePointCriteria sourcePointTargetCriteria = DataSourcePointCriteria.criteria(dataSourceName,
                IdentifierObjectFactory.dataPointTargetName());

        source = sourcePointSourceCriteria.getDataPoint();
        target = sourcePointTargetCriteria.getDataPoint();

        PointLinkCriteria criteria = PointLinkCriteria.change(sourcePointSourceCriteria, sourcePointTargetCriteria);
        allObjectsForPointLinkTestsUtil = new AllObjectsForPointLinkTestsUtil(E2eAbstractRunnable.getNavigationPage(),
                criteria);
        allObjectsForPointLinkTestsUtil.createObjects();
    }

    @After
    public void clean() {
        allObjectsForPointLinkTestsUtil.deleteObjects();
    }

    @Test
    public void test_point_links() {

        for (String expectedValue : values) {

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
}
