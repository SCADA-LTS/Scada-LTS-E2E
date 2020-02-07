package org.scadalts.e2e.test.impl.tests.webservice.pointvalue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.DataPointTestsAbstract;
import org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.impl.services.PointValueWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.WebServiceObjectFactory;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueResponse;

import java.util.Optional;

import static org.junit.Assert.*;

public class PointValueWebServiceTest extends DataPointTestsAbstract {

    private static final DataPointType dataPointType = DataPointType.BINARY;

    private DataPointCriteria dataPointCriteria;
    private String dataPointXid;

    @Before
    public void createDataSourceAndPoint() {

        DataSourceTestsUtil.addDataSource(getDataSourceCriteria());

        dataPointCriteria = new DataPointCriteria("dp_test" + System.nanoTime(), dataPointType);
        EditDataPointPage pointPage = DataSourceTestsUtil.addDataPoint(getDataSourceCriteria(), dataPointCriteria,"true");

        dataPointXid = pointPage.getDataPointXid();

        DataSourceTestsUtil.openDataSourcesPage().enableDataSource(getDataSourceCriteria());

    }

    @After
    public void clean() {
        EditDataSourceWithPointListPage pointListPage = DataSourceTestsUtil
                .openDataSourcesPage()
                .openDataSourceEditor(getDataSourceCriteria());

        pointListPage.openDataPointEditor(dataPointCriteria)
                .waitOnPage(500)
                .deleteDataPoint();

        DataSourceTestsUtil.deteleDataSource(getDataSourceCriteria());
    }

    @Test
    public void test_getValue() {

        //given:
        PointValueParams pointValueParams = new PointValueParams(dataPointXid);

        try(PointValueWebServiceObject pointValueWebServiceObject =
                    WebServiceObjectFactory.newPointValueWebServiceObject()) {

            //when:
            Optional<E2eResponse<PointValueResponse>> responseOpt =
                    pointValueWebServiceObject.getValue(pointValueParams);

            //then:
            assertTrue(responseOpt.isPresent());

            //and:
            E2eResponse<PointValueResponse> response = responseOpt.get();
            assertEquals(200, response.getStatus());

            //and:
            PointValueResponse result = response.getValue();
            assertNotNull(response);
            assertEquals(dataPointXid, result.getXid());
        }
    }

}