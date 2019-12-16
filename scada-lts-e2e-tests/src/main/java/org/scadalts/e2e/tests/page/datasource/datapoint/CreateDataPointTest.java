package org.scadalts.e2e.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.pages.dict.ChangeType;
import org.scadalts.e2e.pages.dict.DataPointType;
import org.scadalts.e2e.pages.page.datasource.DataSourcesPage;
import org.scadalts.e2e.pages.page.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.pages.page.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.tests.page.datasource.DataSourceTestsAbstract;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class CreateDataPointTest extends DataSourceTestsAbstract {

    private DataSourcesPage dataSourcesPage = openDataSourcesPage();
    private EditDataSourceWithPointListPage subjectPage;

    @Before
    public void createDataSource() {
        subjectPage = addDataSource();
    }

    @After
    public void clean() {
        deteleDataSource();
    }

    @Test
    public void test_create_data_point() {

        //given:
        String dataPointName = "dp_test" + System.currentTimeMillis();
        EditDataPointPage pointEditPage = subjectPage.addDataPoint();
        pointEditPage.setDataPointName(dataPointName);
        pointEditPage.enableSettable();
        pointEditPage.selectDataPointType(DataPointType.BINARY);
        pointEditPage.selectChangeType(ChangeType.ALTERNATE);
        pointEditPage.setStartValue("true");

        //when:
        pointEditPage.saveDataPoint();

        //and:
        dataSourcesPage = openDataSourcesPage();
        subjectPage = dataSourcesPage.openDataSourceEditor(getDataSourceName(), getDataSourceType());
        String body = subjectPage.getBodyText();

        //then:
        assertThat(body, containsString(dataPointName));
    }


}