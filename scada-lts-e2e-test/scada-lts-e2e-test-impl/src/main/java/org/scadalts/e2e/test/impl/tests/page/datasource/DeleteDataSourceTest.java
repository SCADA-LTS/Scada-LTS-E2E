package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class DeleteDataSourceTest {

    private DataSourcesPage dataSourcesPageSubject;
    private String dataSourceName = "ds_test" + System.nanoTime();
    private DataSourceCriteria dataSourceToDeleteCriteria;

    @Before
    public void createDataSource() {
        dataSourceToDeleteCriteria = new DataSourceCriteria(dataSourceName, DataSourceType.VIRTUAL_DATA_SOURCE);
        DataSourceTestsUtil.addDataSource(dataSourceToDeleteCriteria);
        dataSourcesPageSubject = DataSourceTestsUtil.openDataSourcesPage();
    }

    @Test
    public void test_delete_data_source() {

        //given
        String body = dataSourcesPageSubject.getBodyText();

        //then:
        assertThat(body, containsString(dataSourceName));

        //when:
        body = dataSourcesPageSubject
                .deleteDataSource(dataSourceToDeleteCriteria)
                .reopen()
                .getBodyText();

        //then:
        assertThat(body, not(containsString(dataSourceName)));
    }
}
