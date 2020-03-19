package org.scadalts.e2e.test.impl.tests.service.pointlinks;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.EventType;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.impl.creators.AllObjectsForPointLinkTestCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class PointLinksServiceTest {

    @Parameterized.Parameters(name = "{index}: source: {0} - {1}, target: {2} - {3}, pointlink type: {4}, script: {5}")
    public static Object[][] data() {
        String unique = IdentifierObjectFactory.unique();
        return new Object[][] {
                {"ds_test1_" + unique, "dp_test1_source_" + unique, "ds_test1_" + unique, "dp_test1_target_" + unique, EventType.UPDATE, Script.sourceValueIncreasedOne()},
                {"ds_test2_" + unique, "dp_test2_source_" + unique, "ds_test2_" + unique, "dp_test2_target_" + unique, EventType.CHANGE, Script.sourceValueIncreasedOne()},
                {"ds_test3_source_" + unique, "dp_test3_source_" + unique, "ds_test3_target_" + unique, "dp_test3_target_" + unique, EventType.UPDATE, Script.sourceValueIncreasedOne()},
                {"ds_test4_source_" + unique, "dp_test4_source_" + unique, "ds_test4_target_" + unique, "dp_test4_target_" + unique, EventType.CHANGE, Script.sourceValueIncreasedOne()},
        };
    }

    private final DataSourcePointCriteria source;
    private final DataSourcePointCriteria target;
    private final EventType eventType;
    private final Script script;


    public PointLinksServiceTest(String dataSoruceSourceName, String dataPointSourceName,
                                         String dataSoruceTargetName, String dataPointTargetName,
                                         EventType eventType, Script script) {
        this.eventType = eventType;

        DataSourceIdentifier dataSourceSourceIdentifier = new DataSourceIdentifier(dataSoruceSourceName, DataSourceType.VIRTUAL_DATA_SOURCE);
        DataSourceIdentifier dataSourceTargetIdentifier = new DataSourceIdentifier(dataSoruceTargetName, DataSourceType.VIRTUAL_DATA_SOURCE);

        DataPointIdentifier dataPointSourceIdentifier = new DataPointIdentifier(dataPointSourceName, DataPointType.NUMERIC);
        DataPointIdentifier dataPointTargetIdentifier = new DataPointIdentifier(dataPointTargetName, DataPointType.NUMERIC);

        source = DataSourcePointCriteria.criteria(dataSourceSourceIdentifier, dataPointSourceIdentifier);
        target = DataSourcePointCriteria.criteria(dataSourceTargetIdentifier, dataPointTargetIdentifier);

        this.script = script;
    }

    private AllObjectsForPointLinkTestCreator allObjectsForPointLinkTestCreator;

    @Before
    public void setup() {
        PointLinkCriteria criteria = PointLinkCriteria.criteria(source, target, eventType, script);
        allObjectsForPointLinkTestCreator = new AllObjectsForPointLinkTestCreator(TestWithPageUtil.getNavigationPage(),
                criteria);
        allObjectsForPointLinkTestCreator.createObjects();
    }

    @After
    public void clean() {
        allObjectsForPointLinkTestCreator.deleteObjects();
    }

    @Test
    public void test_service_point_links() {

        for (String value : ChangePointValuesProvider.paramsToTests()) {

            //given:
            String expectedValue = new BigDecimal(value).add(BigDecimal.ONE).toString();
            logger.info("value: {}, value expected: {}", value, expectedValue);

            Xid sourceXid = source.getDataPoint().getXid();
            Xid targetXid = target.getDataPoint().getXid();

            PointValueParams pointTarget = new PointValueParams(targetXid.getValue());
            CmpParams cmpParams = CmpParams.builder()
                    .error("")
                    .resultOperationSave("")
                    .value(value)
                    .xid(sourceXid.getValue())
                    .build();

            //when:
            TestWithoutPageUtil.setValue(cmpParams);

            //and when:
            E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getValue(pointTarget, expectedValue);
            PointValueResponse getResult = getResponse.getValue();

            //then:
            assertEquals(200, getResponse.getStatus());
            assertNotNull(getResult);
            assertEquals(targetXid.getValue(), getResult.getXid());
            assertEquals(expectedValue, getResult.getFormattedValue());
        }
    }
}
