package org.scadalts.e2e.test.impl.tests.service.pointvalue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.runners.E2eServiceTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.ServiceTestsUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(E2eServiceTestRunner.class)
public class PointValueServiceTest {

    private String dataPointXid;
    private DataPointCriteria dataPointCriteria;
    private String dataPointStartValue;
    private DataSourcePointObjectsCreator dataSourcesPageTestsUtil;

    @Before
    public void createDataSourceAndPoint() {
        dataPointStartValue = "1223.0";
        dataPointCriteria = DataPointCriteria.noChange(DataPointType.NUMERIC, dataPointStartValue);
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataSourcesPageTestsUtil = new DataSourcePointObjectsCreator(E2eAbstractRunnable.getNavigationPage(),
                dataSourceCriteria, dataPointCriteria);
        EditDataSourceWithPointListPage pointPage = dataSourcesPageTestsUtil.createObjects()
                .openDataSourceEditor(dataSourceCriteria);
        EditDataPointPage editDataPointPage = pointPage.openDataPointEditor(dataPointCriteria);
        dataPointXid = editDataPointPage.getDataPointXid();
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.deleteObjects();
    }

    @Test
    public void test_getValue_data_point_then_status_http_200() {

        //given:
        PointValueParams pointValueParams = new PointValueParams(dataPointXid);

        //when:
        E2eResponse<PointValueResponse> getResponse = ServiceTestsUtil.getValue(pointValueParams);

        //then:
        assertEquals(200, getResponse.getStatus());
    }

    @Test
    public void test_getValue_data_point_then_expect_xid() {

        //given:
        PointValueParams pointValueParams = new PointValueParams(dataPointXid);

        //when:
        E2eResponse<PointValueResponse> getResponse = ServiceTestsUtil.getValue(pointValueParams);
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
        E2eResponse<PointValueResponse> getResponse = ServiceTestsUtil.getValue(pointValueParams, dataPointStartValue);
        PointValueResponse getResult = getResponse.getValue();

        //then:
        assertNotNull(getResult);
        assertEquals(dataPointStartValue, getResult.getFormattedValue());
    }

}