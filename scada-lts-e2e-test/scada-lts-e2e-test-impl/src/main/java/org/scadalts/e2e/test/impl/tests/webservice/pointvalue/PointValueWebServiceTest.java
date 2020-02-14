package org.scadalts.e2e.test.impl.tests.webservice.pointvalue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil;
import org.scadalts.e2e.webservice.core.exceptions.WebServiceObjectException;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.impl.services.PointValueWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.WebServiceObjectFactory;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueResponse;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(E2eTestRunner.class)
public class PointValueWebServiceTest {

    private static final DataPointType dataPointType = DataPointType.BINARY;

    private final NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
    private final DataSourceTestsUtil testsUtil = new DataSourceTestsUtil(navigationPage);

    private DataPointCriteria dataPointCriteria;
    private String dataPointXid;

    private DataSourceCriteria dataSourceCriteria;

    @Rule
    public ExpectedException invalidInputException1= ExpectedException.none();

    @Before
    public void createDataSourceAndPoint() throws ConfigureTestException {

        dataSourceCriteria = testsUtil.createDataSourceCriteria();
        testsUtil.addDataSource(dataSourceCriteria);

        dataPointCriteria = new DataPointCriteria("dp_test" + System.nanoTime(), dataPointType);
        EditDataPointPage pointPage = testsUtil.addDataPoint(dataSourceCriteria, dataPointCriteria,"true");

        dataPointXid = pointPage.getDataPointXid();

        testsUtil.openDataSourcesPage().enableDataSource(dataSourceCriteria);

    }

    @After
    public void clean() throws ConfigureTestException {
        EditDataSourceWithPointListPage pointListPage = testsUtil.openDataSourcesPage()
                .openDataSourceEditor(dataSourceCriteria);

        pointListPage.openDataPointEditor(dataPointCriteria)
                .waitOnPage(500)
                .deleteDataPoint();

        testsUtil.deteleDataSource(dataSourceCriteria);
    }

    @Test
    public void test_getValue() throws WebServiceObjectException {

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