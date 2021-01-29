package org.scadalts.e2e.test.impl.tests.diagnostic.eventdetector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class EventDetectorDiagnosticTest {

    private static DataSourcesPage dataSourcesPage;

    @Parameterized.Parameters(name = "{index}: dataSource: {0}")
    public static List<DataSourceCriteria> data() {
        dataSourcesPage = TestWithPageUtil.preparingTest()
                .openDataSources();

        DataSourceCriteria dataSourceForTestEventDetector = DataSourceCriteria.criteriaSecond(
                new DataSourceIdentifier(TestImplConfiguration.dataSourceNameEventDetectorTest,
                        DataSourceType.VIRTUAL_DATA_SOURCE));

        List<DataSourceCriteria> dataSourceCriterias = dataSourcesPage.getDataSources();
        dataSourceCriterias.remove(dataSourceForTestEventDetector);
        return dataSourceCriterias;
    }

    private final DataSourceCriteria dataSourceCriteria;

    public EventDetectorDiagnosticTest(DataSourceCriteria dataSourceCriteria) {
        this.dataSourceCriteria = dataSourceCriteria;
    }

    @Before
    public void disableDataSource() {
        if(dataSourcesPage.isEnabledDataSource(dataSourceCriteria.getIdentifier()))
            dataSourcesPage.disableDataSource(dataSourceCriteria.getIdentifier());
    }

    @Test
    public void test_event_detector() {

        for (String expectedValue : ChangePointValuesProvider.paramsToTests()) {

            //given:
            PointValueParams pointValueParams = new PointValueParams(TestImplConfiguration.dataPointToReadXid);
            CmpParams cmpParams = CmpParams.builder()
                    .error("")
                    .resultOperationSave("")
                    .value(expectedValue)
                    .dataPointXid(TestImplConfiguration.dataPointToChangeXid)
                    .build();

            //when:
            E2eResponse<CmpParams> setResponse = TestWithoutPageUtil.setDataPointValue(cmpParams);
            CmpParams setResult = setResponse.getValue();

            //then:
            assertEquals(200, setResponse.getStatus());
            assertNotNull(setResult);
            assertEquals("", setResult.getError());
            assertEquals(TestImplConfiguration.dataPointToChangeXid, setResult.getDataPointXid());
            assertEquals(expectedValue, setResult.getValue());

            //and when:
            E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getDataPointValue(pointValueParams,
                    expectedValue, TestImplConfiguration.waitingAfterSetPointValueMs);
            PointValueResponse getResult = getResponse.getValue();

            //then:
            assertEquals(200, getResponse.getStatus());
            assertNotNull(getResult);
            assertEquals(TestImplConfiguration.dataPointToReadXid, getResult.getXid());
            assertEquals(expectedValue, getResult.getFormattedValue());
        }
    }
}
