package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.app.AppPage;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class CreateDataPointPageTest {

    private static final DataPointType dataPointType = DataPointType.BINARY;
    private static final ChangeType changeType = ChangeType.NO_CHANGE;
    private final DataPointIdentifier dataPointCreatedName = IdentifierObjectFactory.dataPointName(dataPointType);

    private DataPointCriteria dataPointCreatedCriteria;
    private DataSourceCriteria dataSourceCriteria;

    private DataSourcesPage dataSourcesPage;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;

    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;

    NavigationPage navigationPage;

    @Before
    public void createDataSource() {
        dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataPointCreatedCriteria = DataPointCriteria.builder()
                .dataPointProperties(DataPointProperties.empty())
                .xid(Xid.dataPoint())
                .identifier(dataPointCreatedName)
                .changeType(changeType)
                .startValue("true")
                .build();

        navigationPage = TestWithPageUtil.openNavigationPage();

        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, dataPointCreatedCriteria);

        dataSourcesPage = dataSourcePointObjectsCreator.openPage();
        editDataSourceWithPointListPageSubject = dataSourcePointObjectsCreator.createDataSources();
    }

    @After
    public void clean() {
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_create_data_point() {

        //when:
        editDataSourceWithPointListPageSubject.addDataPoint()
                .setName(dataPointCreatedName)
                .enableSettable()
                .setDataPointType(dataPointType)
                .setChangeType(changeType)
                .setStartValue(dataPointCreatedCriteria)
                .save()
                .enableDataPoint(dataPointCreatedCriteria.getIdentifier());

        //and:
        String body = dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .waitOnPointsTable()
                .getBodyText();

        //then:
        assertThat(body, containsString(dataPointCreatedName.getValue()));
    }
}