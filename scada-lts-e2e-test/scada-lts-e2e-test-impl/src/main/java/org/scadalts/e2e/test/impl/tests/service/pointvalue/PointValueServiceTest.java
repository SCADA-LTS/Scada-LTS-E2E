package org.scadalts.e2e.test.impl.tests.service.pointvalue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PointValueServiceTest {

    private String dataPointXid;
    private String dataPointStartValue;
    private VirtualDataSourcePointObjectsCreator dataSourcePointObjectsCreator;

    @Before
    public void createDataSourceAndPoint() {
        dataPointStartValue = "1223.0";
        VirtualDataPointCriteria dataPointCriteria = VirtualDataPointCriteria.noChange(DataPointType.NUMERIC, dataPointStartValue);
        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.virtualDataSourceSecond();
        dataSourcePointObjectsCreator = new VirtualDataSourcePointObjectsCreator(TestWithPageUtil.openNavigationPage(),
                dataSourceCriteria, dataPointCriteria);
        EditDataSourceWithPointListPage pointPage = dataSourcePointObjectsCreator.createObjects()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier());
        EditDataPointPage editDataPointPage = pointPage.openDataPointEditor(dataPointCriteria.getIdentifier());
        dataPointXid = editDataPointPage.getXid();
    }

    @After
    public void clean() {
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_getValue_data_point_then_status_http_200() {

        //given:
        PointValueParams pointValueParams = new PointValueParams(dataPointXid);

        //when:
        E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getDataPointValue(pointValueParams);

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_getValue_data_point_then_expect_xid() {

        //given:
        PointValueParams pointValueParams = new PointValueParams(dataPointXid);

        //when:
        E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getDataPointValue(pointValueParams);
        PointValueResponse getResult = getResponse.getValue();

        //then:
        assertNotNull(getResult);
        assertEquals(dataPointXid, getResult.getXid());
    }

    @Test
    public void test_getValue_data_point_then_expect_value() {

        //given:
        PointValueParams pointValueParams = new PointValueParams(dataPointXid);

        //when:
        E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getDataPointValue(pointValueParams, dataPointStartValue,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        PointValueResponse getResult = getResponse.getValue();

        //then:
        assertNotNull(getResult);
        assertEquals(dataPointStartValue, getResult.getFormattedValue());
    }

}