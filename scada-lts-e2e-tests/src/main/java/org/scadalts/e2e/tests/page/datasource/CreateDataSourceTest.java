package org.scadalts.e2e.tests.page.datasource;

import org.junit.After;
import org.junit.Test;
import org.scadalts.e2e.pages.dict.DataSourceType;
import org.scadalts.e2e.pages.dict.UpdatePeriodType;
import org.scadalts.e2e.pages.page.datasource.DataSourcesPage;
import org.scadalts.e2e.pages.page.datasource.EditDataSourcePage;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class CreateDataSourceTest extends DataSourceTestsAbstract {

    private DataSourcesPage subjectPage = openDataSourcesPage();
    private static final String dataSourceName = "ds_test" + System.currentTimeMillis();
    private static final DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;

    @After
    public void clean() {
        deteleDataSource(dataSourceName, dataSourceType);
    }

    @Test
    public void test_create_data_source() {

        //given:
        subjectPage.selectDataSourceType(dataSourceType);
        EditDataSourcePage edit = subjectPage.openDataSourceCreator();
        edit.selectUpdatePeriodType(UpdatePeriodType.SECOUND);
        edit.setUpdatePeriods(12);
        edit.setDataSourceName(dataSourceName);

        //when:
        edit.saveSaveDataSource();

        //and:
        subjectPage = openDataSourcesPage();
        String body = subjectPage.getBodyText();

        //then:
        assertThat(body, containsString(dataSourceName));
    }
}
