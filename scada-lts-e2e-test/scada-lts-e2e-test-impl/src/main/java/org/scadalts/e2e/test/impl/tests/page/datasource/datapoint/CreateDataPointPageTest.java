package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(TestWithPageRunner.class)
public class CreateDataPointPageTest {

    private static final DataPointType dataPointType = DataPointType.BINARY;
    private static final ChangeType changeType = ChangeType.ALTERNATE;
    private final DataPointIdentifier dataPointToCreateName = IdentifierObjectFactory.dataPointName(dataPointType);

    private DataPointCriteria dataPointCreatedCriteria;
    private DataSourceCriteria dataSourceCriteria;

    private DataSourcesPage dataSourcesPage;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;

    private DataSourcePointObjectsCreator dataSourcesPageTestsUtil;

    @Before
    public void createDataSource() {
        dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataPointCreatedCriteria = DataPointCriteria.builder()
                .xid(Xid.xidForDataPoint())
                .identifier(dataPointToCreateName)
                .changeType(changeType)
                .startValue("true")
                .build();

        dataSourcesPageTestsUtil = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(), dataSourceCriteria, dataPointCreatedCriteria);

        dataSourcesPage = dataSourcesPageTestsUtil.openPage();
        editDataSourceWithPointListPageSubject = dataSourcesPageTestsUtil.createDataSources();
    }

    @After
    public void clean() {
        dataSourcesPageTestsUtil.deleteObjects();
    }

    @Test
    public void test_create_data_point() {

        //when:
        editDataSourceWithPointListPageSubject.addDataPoint()
                .setDataPointName(dataPointToCreateName)
                .enableSettable()
                .selectDataPointType(dataPointType)
                .selectChangeType(changeType)
                .setStartValue(dataPointCreatedCriteria)
                .saveDataPoint()
                .enableDataPoint(dataPointCreatedCriteria.getIdentifier());

        //and:
        String body = dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .getBodyText();

        //then:
        assertThat(body, containsString(dataPointToCreateName.getValue()));
    }


}