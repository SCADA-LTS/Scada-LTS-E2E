package org.scadalts.e2e.tests.page.datasource;

import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.pages.dict.DataSourceType;
import org.scadalts.e2e.pages.page.datasource.DataSourcesPage;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class DeleteDataSourceTest extends DataSourceTestsAbstract {

    private DataSourcesPage subjectPage;
    private static final String dataSourceName = "ds_test" + System.currentTimeMillis();
    private static final DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;

    @Before
    public void createDataSource() {
        addDataSource(dataSourceName, dataSourceType);
        subjectPage = openDataSourcesPage();
    }

    @Test
    public void test_delete_data_source() {
        //when:
        subjectPage.deleteDataSource(dataSourceName, dataSourceType);

        //and:
        String body = subjectPage.getBodyText();

        //then:
        assertThat(body, not(containsString(dataSourceName)));
    }
}
