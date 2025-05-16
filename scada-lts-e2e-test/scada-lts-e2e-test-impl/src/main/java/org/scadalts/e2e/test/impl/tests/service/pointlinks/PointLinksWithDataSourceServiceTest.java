package org.scadalts.e2e.test.impl.tests.service.pointlinks;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.EventType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksDetailsPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.creators.AllObjectsForPointLinkTestCreator;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.scadalts.e2e.test.impl.creators.PointLinksObjectsCreator.isUpdate;

@Log4j2
@RunWith(Parameterized.class)
public class PointLinksWithDataSourceServiceTest {

    @Parameterized.Parameters(name = "{index}: datasource: {0}, point link type: {1}")
    public static Object[][] data() { return new Object[][] {

                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.SECOND, 1), EventType.CHANGE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.SECOND, 5), EventType.CHANGE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.SECOND, 10), EventType.CHANGE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.MILLISECOND, 100), EventType.CHANGE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.MILLISECOND, 1000), EventType.CHANGE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.MILLISECOND, 3000), EventType.CHANGE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.MILLISECOND, 10000), EventType.CHANGE},

                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.SECOND, 1), EventType.UPDATE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.SECOND, 5), EventType.UPDATE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.SECOND, 10), EventType.UPDATE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.MILLISECOND, 100), EventType.UPDATE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.MILLISECOND, 1000), EventType.UPDATE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.MILLISECOND, 3000), EventType.UPDATE},
                {UpdateDataSourceCriteria.criteria(IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                        UpdatePeriodType.MILLISECOND, 10000), EventType.UPDATE},
        };
    }

    private final UpdateDataSourceCriteria dataSourceCriteria;
    private final EventType eventType;

    public PointLinksWithDataSourceServiceTest(UpdateDataSourceCriteria dataSourceCriteria, EventType eventType) {
        this.dataSourceCriteria = dataSourceCriteria;
        this.eventType = eventType;
    }

    private AllObjectsForPointLinkTestCreator allObjectsForPointLinkTestCreator;
    private VirtualDataPointCriteria source;
    private VirtualDataPointCriteria target;
    private PointLinksPage pointLinksPage;
    private VirtualPointLinkCriteria criteria;

    @Before
    public void setup() {

        VirtualDataSourcePointCriteria sourcePointSourceCriteria = VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria,
                IdentifierObjectFactory.dataPointSourceName(DataPointType.NUMERIC));
        VirtualDataSourcePointCriteria sourcePointTargetCriteria = VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria,
                IdentifierObjectFactory.dataPointTargetName(DataPointType.NUMERIC));

        source = sourcePointSourceCriteria.getDataPoint();
        target = sourcePointTargetCriteria.getDataPoint();

        criteria = VirtualPointLinkCriteria.criteria(sourcePointSourceCriteria, sourcePointTargetCriteria,
                eventType, Script.empty());

        WatchListCriteria watchListCriteria = WatchListCriteria.criteria(sourcePointSourceCriteria.getIdentifier(),
                sourcePointTargetCriteria.getIdentifier());
        allObjectsForPointLinkTestCreator = new AllObjectsForPointLinkTestCreator(TestWithPageUtil.openNavigationPage(),
                criteria, watchListCriteria.getIdentifier());
        allObjectsForPointLinkTestCreator.createObjects();
        pointLinksPage = allObjectsForPointLinkTestCreator.openPage();
    }

    @After
    public void clean() {
        if(allObjectsForPointLinkTestCreator != null)
            allObjectsForPointLinkTestCreator.deleteObjects();
    }

    @Test
    public void test_service_point_links() {
        List<String> values = new ArrayList<>(ChangePointValuesProvider.paramsToTests());
        String previousValue = "";
        String expectedValue = "";
        PointLinksDetailsPage pointLinksDetailsPage = pointLinksPage.openPointLinkEditor(criteria);

        for(int i = 0 ; i < values.size() ; i++) {

            //given:
            Script script = Script.sourceValueIncreased(i);
            String value = values.get(i);

            pointLinksDetailsPage.setScript(script)
                    .savePointLink();

            expectedValue = isUpdate(eventType, previousValue, value) ? script.executeInJava(value) : expectedValue;
            logger.info("value: {}, expected: {}, script: {}", value, expectedValue, script);

            Xid sourceXid = source.getXid();
            Xid targetXid = target.getXid();

            PointValueParams pointTarget = new PointValueParams(targetXid.getValue());
            CmpParams cmpParams = CmpParams.builder()
                    .error("")
                    .resultOperationSave("")
                    .value(value)
                    .dataPointXid(sourceXid.getValue())
                    .build();

            //when:
            TestWithoutPageUtil.setDataPointValue(cmpParams);

            //and when:
            E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getDataPointValue(pointTarget, expectedValue,
                    TestImplConfiguration.waitingAfterSetPointValueMs);
            PointValueResponse getResult = getResponse.getValue();

            //then:
            assertEquals(200, getResponse.getStatus());
            assertNotNull(getResult);
            assertEquals(targetXid.getValue(), getResult.getXid());
            assertEquals(expectedValue, getResult.getFormattedValue());
            previousValue = value;
        }
    }
}
