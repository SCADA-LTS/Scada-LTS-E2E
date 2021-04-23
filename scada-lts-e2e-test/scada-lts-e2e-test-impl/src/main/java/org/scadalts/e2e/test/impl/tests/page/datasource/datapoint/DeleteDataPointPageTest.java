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
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class DeleteDataPointPageTest {

    private final DataPointIdentifier dataPointToDeleteName = IdentifierObjectFactory
            .dataPointDeleteName(DataPointType.BINARY);

    private DataPointCriteria dataPointToDeleteCriteria;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcesPageCreator;

    @Before
    public void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.binaryAlternate();
        DataPointCriteria dataPointCriteria2 = DataPointCriteria.binaryAlternate();

        dataPointToDeleteCriteria = DataPointCriteria.builder()
                .dataPointProperties(DataPointProperties.empty())
                .xid(Xid.dataPoint())
                .identifier(dataPointToDeleteName)
                .changeType(ChangeType.NO_CHANGE)
                .startValue("true")
                .build();

        dataSourcesPageCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.openNavigationPage(), dataSourceCriteria, dataPointCriteria,
                dataPointToDeleteCriteria, dataPointCriteria2);
        editDataSourceWithPointListPageSubject = dataSourcesPageCreator.createObjects()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .acceptAlertOnPage();
    }

    @After
    public void clean() {
        if(dataSourcesPageCreator != null)
            dataSourcesPageCreator.deleteObjects();
    }

    @Test
    public void test_delete_data_point() {

        //when:
        String bodyBeforeDelete = editDataSourceWithPointListPageSubject.getBodyText();

        //then:
        assertThat(bodyBeforeDelete, containsString(dataPointToDeleteName.getValue()));

        //and when:
        String bodyAfterDelete = editDataSourceWithPointListPageSubject
                .openDataPointEditor(dataPointToDeleteCriteria.getIdentifier())
                .deleteDataPoint()
                .waitOnPageWhileVisibleDataPoint(dataPointToDeleteCriteria.getIdentifier())
                .getBodyText();

        //then:
        assertThat(bodyAfterDelete, not(containsString(dataPointToDeleteName.getValue())));
    }
}
