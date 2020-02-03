package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil;

import static org.junit.Assert.assertTrue;

@Log4j2
public class CreateDataPointTest extends DataPointTestsAbstract {

    private static final DataPointType dataPointType = DataPointType.BINARY;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private final String dataPointToCreateName = "dp_test" + System.nanoTime();
    private DataPointCriteria dataPointCreatedCriteria = new DataPointCriteria(dataPointToCreateName, dataPointType);


    @Before
    public void createDataSource() {
        editDataSourceWithPointListPageSubject = DataSourceTestsUtil.addDataSource(getDataSourceCriteria());
        editDataSourceWithPointListPageSubject.dataSourceOnOff();
    }

    @After
    public void clean() {
        DataSourceTestsUtil.openDataSourcesPage()
                .openDataSourceEditor(getDataSourceCriteria())
                .openDataPointEditor(dataPointCreatedCriteria)
                .deleteDataPoint();
        DataSourceTestsUtil.deteleDataSource(getDataSourceCriteria());
    }

    @Test
    public void test_create_data_point() {

        //when:
        editDataSourceWithPointListPageSubject.addDataPoint()
                .setDataPointName(dataPointToCreateName)
                .enableSettable()
                .selectDataPointType(dataPointType)
                .selectChangeType(ChangeType.ALTERNATE)
                .setStartValue("true")
                .saveDataPoint()
                .enableDataPoint(dataPointCreatedCriteria);

        //then:
        boolean result = DataSourceTestsUtil.openDataSourcesPage()
                .openDataSourceEditor(getDataSourceCriteria())
                .containsObject(dataPointCreatedCriteria);

        assertTrue(result);
    }


}