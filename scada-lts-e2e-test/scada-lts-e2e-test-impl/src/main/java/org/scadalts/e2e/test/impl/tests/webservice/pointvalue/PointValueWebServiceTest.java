package org.scadalts.e2e.test.impl.tests.webservice.pointvalue;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourcesPageTestsUtil;
import org.scadalts.e2e.webservice.core.exceptions.WebServiceObjectException;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.impl.services.PointValueWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.WebServiceObjectFactory;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueResponse;

import java.util.Optional;

import static org.junit.Assert.*;

@Log4j2
@RunWith(E2eTestRunner.class)
public class PointValueWebServiceTest {

    private String dataPointXid;
    private DataSourcesPageTestsUtil dataSourcesPageTestsUtil;

    @Before
    public void createDataSourceAndPoint() throws InterruptedException {

        DataPointCriteria dataPointCriteria = new DataPointCriteria("dp_test" + System.nanoTime(), DataPointType.NUMERIC, ChangeType.NO_CHANGE);
        dataSourcesPageTestsUtil = new DataSourcesPageTestsUtil(E2eAbstractRunnable.getNavigationPage(), dataPointCriteria);
        dataSourcesPageTestsUtil.addDataSources();
        EditDataPointPage pointPage = dataSourcesPageTestsUtil.addDataPoints("1234");

        dataPointXid = pointPage.getDataPointXid();

        Thread.sleep(TestImplConfiguration.waitingAfterSetPointValueMs);
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.clean();
    }

    @Test
    public void test_getValue_data_point() throws WebServiceObjectException {

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
            assertNotNull(result);
            assertEquals(dataPointXid, result.getXid());
        }
    }

}