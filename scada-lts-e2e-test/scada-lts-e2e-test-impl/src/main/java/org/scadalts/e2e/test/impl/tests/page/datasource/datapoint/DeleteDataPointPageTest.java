package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(TestWithPageRunner.class)
public class DeleteDataPointPageTest {

    private final DataPointIdentifier dataPointToDeleteName = new DataPointIdentifier("dp_test_to_delete_" + System.nanoTime(),DataPointType.BINARY);

    private DataPointCriteria dataPointToDeleteCriteria;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcesPageCreator;

    @Before
    public void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.binaryAlternate();
        DataPointCriteria dataPointCriteria2 = DataPointCriteria.binaryAlternate();

        dataPointToDeleteCriteria = DataPointCriteria.builder()
                .xid(Xid.xidForDataPoint())
                .identifier(dataPointToDeleteName)
                .changeType(ChangeType.ALTERNATE)
                .startValue("true")
                .build();

        dataSourcesPageCreator = new DataSourcePointObjectsCreator(TestWithPageUtil.getNavigationPage(), dataSourceCriteria, dataPointCriteria,
                dataPointToDeleteCriteria, dataPointCriteria2);
        editDataSourceWithPointListPageSubject = dataSourcesPageCreator.createObjects()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .acceptAlertOnPage();
    }

    @After
    public void clean() {
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
                .waitOnPageWhileVisibleObject(dataPointToDeleteCriteria.getIdentifier())
                .getBodyText();

        //then:
        assertThat(bodyAfterDelete, not(containsString(dataPointToDeleteName.getValue())));
    }
}
