package org.scadalts.e2e.test.impl.tests.page.datasource;

import org.junit.After;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.scadalts.e2e.test.impl.utils.DataSourceTestsUtil.openDataSourcesPage;

public class CreateDataSourceTest {

    private final String dataSourceName = "ds_test" + System.nanoTime();
    private final DataSourceType dataSourceType = DataSourceType.VIRTUAL_DATA_SOURCE;

    @After
    public void clean() {
        DataSourceCriteria criteria = new DataSourceCriteria(dataSourceName, dataSourceType);
        DataSourceTestsUtil.deteleDataSource(criteria);
    }

    @Test
    public void test_create_data_source() {

        //when:
        openDataSourcesPage().openDataSourceCreator(dataSourceType)
                .selectUpdatePeriodType(UpdatePeriodType.SECOUND)
                .setUpdatePeriods(13)
                .setDataSourceName(dataSourceName)
                .saveDataSource()
                .dataSourceOnOff();

        //then:
        String body = openDataSourcesPage().getBodyText();

        assertThat(body, containsString(dataSourceName));
    }
}
