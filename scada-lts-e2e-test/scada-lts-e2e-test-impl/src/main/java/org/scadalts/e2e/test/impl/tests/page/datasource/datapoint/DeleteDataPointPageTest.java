package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteDataPointPageTest {

    private final DataPointIdentifier dataPointToDeleteName = IdentifierObjectFactory
            .dataPointDeleteName(DataPointType.BINARY);

    private VirtualDataPointCriteria dataPointToDeleteCriteria;
    private EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcesPageCreator;

    @Before
    public void createDataSourceAndPoint() {

        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.virtualDataSourceSecond();
        VirtualDataPointCriteria dataPointCriteria = VirtualDataPointCriteria.binaryNoChange();
        VirtualDataPointCriteria dataPointCriteria2 = VirtualDataPointCriteria.binaryNoChange();

        dataPointToDeleteCriteria = VirtualDataPointCriteria.builder()
                .dataPointProperties(DataPointProperties.empty())
                .xid(Xid.dataPoint())
                .identifier(dataPointToDeleteName)
                .changeType(ChangeType.NO_CHANGE)
                .startValue("true")
                .enabled(true)
                .build();

        dataSourcesPageCreator = new VirtualDataSourcePointObjectsCreator(TestWithPageUtil.openNavigationPage(), dataSourceCriteria, dataPointCriteria,
                dataPointToDeleteCriteria, dataPointCriteria2);
        editDataSourceWithPointListPageSubject = dataSourcesPageCreator.createObjects()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier());
    }

    @After
    public void clean() {
        if(dataSourcesPageCreator != null)
            dataSourcesPageCreator.deleteObjects();
    }

    @Test
    public void test_delete_data_point() {

        //then:
        assertTrue(editDataSourceWithPointListPageSubject.containsObject(dataPointToDeleteCriteria.getIdentifier()));

        //and when:
        editDataSourceWithPointListPageSubject
                .openDataPointEditor(dataPointToDeleteCriteria.getIdentifier())
                .deleteDataPoint()
                .waitOnPageWhileVisibleDataPoint(dataPointToDeleteCriteria.getIdentifier());

        //then:
        assertFalse(editDataSourceWithPointListPageSubject.containsObject(dataPointToDeleteCriteria.getIdentifier()));
    }
}
