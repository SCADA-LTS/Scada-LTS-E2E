package org.scadalts.e2e.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.pages.dict.DataPointType;
import org.scadalts.e2e.pages.page.datasource.DataSourcesPage;
import org.scadalts.e2e.pages.page.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.pages.page.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.tests.page.datasource.DataSourceTestsAbstract;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class DeleteDataPointTest extends DataSourceTestsAbstract {

    private DataSourcesPage dataSourcesPage;
    private EditDataSourceWithPointListPage subjectPage;

    private static final String dataPointName = "dp_test" + System.currentTimeMillis();
    private static final String dataPointName2 = "dp_test2" + System.currentTimeMillis();
    private static final String dataPointName3 = "dp_test3" + System.currentTimeMillis();

    private static final DataPointType dataPointType = DataPointType.BINARY;

    @Before
    public void createDataSourceAndPoint() {

        subjectPage = addDataSource();

        addDataPoint(dataPointName, dataPointType, "true");
        addDataPoint(dataPointName2, dataPointType, "false");
        addDataPoint(dataPointName3, dataPointType, "false");

        dataSourcesPage = openDataSourcesPage();
    }

    @After
    public void clean() {
        deteleDataSource();
    }

    @Test
    public void test_delete_data_point() {

        //given:
        subjectPage = dataSourcesPage.openDataSourceEditor(getDataSourceName(), getDataSourceType());
        EditDataPointPage pointEditPage = subjectPage.openDataPointEditor(dataPointName2, dataPointType);

        //when:
        pointEditPage.deleteDataPoint();

        //and:
        dataSourcesPage = openDataSourcesPage();
        subjectPage = dataSourcesPage.openDataSourceEditor(getDataSourceName(), getDataSourceType());
        String body = subjectPage.getBodyText();

        //then:
        assertThat(body, not(containsString(dataPointName2)));
    }
}
