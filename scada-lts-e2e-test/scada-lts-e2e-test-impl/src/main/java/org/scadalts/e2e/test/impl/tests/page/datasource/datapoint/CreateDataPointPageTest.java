package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreateDataPointPageTest {

    private static VirtualDataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private static EditDataSourceWithPointListPage editDataSourceWithPointListPageSubject;
    private static UpdateDataSourceCriteria dataSourceCriteria;

    @Parameterized.Parameters(name = "{index}: xid: {0}, dataType: {1}, changeType: {2}, startValue: {3}, settable: {4}, enabled: {5}, enabled: {6}, minValue: {6}, maxValue: {6}, changeValue: {6}")
    public static Object[][] data() {
        return new Object[][] {

                {Xid.dataPoint(), DataPointType.BINARY, ChangeType.NO_CHANGE, "true", true, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.BINARY, ChangeType.NO_CHANGE, "true", false, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.BINARY, ChangeType.NO_CHANGE, "true", true, false, "", "", ""},
                {Xid.dataPoint(), DataPointType.BINARY, ChangeType.NO_CHANGE, "true", false, false, "", "", ""},

                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.NO_CHANGE, "123", true, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.NO_CHANGE, "234", false, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.NO_CHANGE, "345", true, false, "", "", ""},
                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.NO_CHANGE, "345", false, false, "", "", ""},

                {Xid.dataPoint(), DataPointType.ALPHANUMERIC, ChangeType.NO_CHANGE, "abc", true, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.ALPHANUMERIC, ChangeType.NO_CHANGE, "cba", false, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.ALPHANUMERIC, ChangeType.NO_CHANGE, "bac", true, false, "", "", ""},
                {Xid.dataPoint(), DataPointType.ALPHANUMERIC, ChangeType.NO_CHANGE, "bac", false, false, "", "", ""},

                {Xid.dataPoint(), DataPointType.MULTISTATE, ChangeType.NO_CHANGE, "1", true, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.MULTISTATE, ChangeType.NO_CHANGE, "2", false, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.MULTISTATE, ChangeType.NO_CHANGE, "3", true, false, "", "", ""},
                {Xid.dataPoint(), DataPointType.MULTISTATE, ChangeType.NO_CHANGE, "3", false, false, "", "", ""},

                {Xid.dataPoint(), DataPointType.BINARY, ChangeType.ALTERNATE, "true", true, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.BINARY, ChangeType.ALTERNATE, "false", false, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.BINARY, ChangeType.ALTERNATE, "true", true, false, "", "", ""},
                {Xid.dataPoint(), DataPointType.BINARY, ChangeType.ALTERNATE, "false", false, false, "", "", ""},

                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.RANDOM, "123", true, true, "0", "123", ""},
                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.RANDOM, "234", false, true, "123", "234", ""},
                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.RANDOM, "345", true, false, "234", "345", ""},
                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.RANDOM, "456", false, false, "-345", "456", ""},

                {Xid.dataPoint(), DataPointType.ALPHANUMERIC, ChangeType.NO_CHANGE, "abc", true, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.ALPHANUMERIC, ChangeType.NO_CHANGE, "cba", false, true, "", "", ""},
                {Xid.dataPoint(), DataPointType.ALPHANUMERIC, ChangeType.NO_CHANGE, "bac", true, false, "", "", ""},
                {Xid.dataPoint(), DataPointType.ALPHANUMERIC, ChangeType.NO_CHANGE, "bac", false, false, "", "", ""},

                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.INCREMENT, "1", true, true, "1", "6", "1"},
                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.INCREMENT, "2", false, true, "2", "8", "2"},
                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.INCREMENT, "3", true, false, "3", "9", "3"},
                {Xid.dataPoint(), DataPointType.NUMERIC, ChangeType.INCREMENT, "3", false, false, "4", "12", "4"},

        };
    }

    private DataSourcesPage dataSourcesPage;
    private final VirtualDataPointCriteria dataPointCreatedCriteria;

    public CreateDataPointPageTest(Xid xid, DataPointType dataPointType,
                                   ChangeType changeType, String startValue, boolean settable, boolean enabled,
                                   String minValue, String maxValue, String changeValue) {
        this.dataPointCreatedCriteria = VirtualDataPointCriteria.builder()
                .dataPointProperties(DataPointProperties.empty())
                .xid(xid)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .changeType(changeType)
                .startValue(startValue)
                .settable(settable)
                .enabled(enabled)
                .minValue(minValue)
                .maxValue(maxValue)
                .changeValue(changeValue)
                .build();
    }

    @BeforeClass
    public static void createDataSource() {
        dataSourceCriteria = UpdateDataSourceCriteria.virtualDataSourceSecond();
        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();
        dataSourcePointObjectsCreator = new VirtualDataSourcePointObjectsCreator(navigationPage, dataSourceCriteria);
        editDataSourceWithPointListPageSubject = dataSourcePointObjectsCreator.createObjects()
                .openDataSourceEditor(dataSourceCriteria);
    }

    @AfterClass
    public static void clean() {
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }


    @Before
    public void openDataSourcesPage() {
        dataSourcesPage = dataSourcePointObjectsCreator.openPage();
        editDataSourceWithPointListPageSubject = dataSourcesPage.openDataSourceEditor(dataSourceCriteria);
    }

    @Test
    public void test_create_data_point_then_exists() {

        //when:
        editDataSourceWithPointListPageSubject.addDataPoint()
                .setName(dataPointCreatedCriteria.getIdentifier())
                .setXid(dataPointCreatedCriteria.getXid())
                .setSettable(dataPointCreatedCriteria.isSettable())
                .setDataPointType(dataPointCreatedCriteria.getIdentifier().getType())
                .setChangeType(dataPointCreatedCriteria.getChangeType())
                .setMaxValue(dataPointCreatedCriteria)
                .setMinValue(dataPointCreatedCriteria)
                .setChangeValue(dataPointCreatedCriteria)
                .setStartValue(dataPointCreatedCriteria)
                .save()
                .enableDataPoint(dataPointCreatedCriteria);

        //and:
        editDataSourceWithPointListPageSubject = dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .waitOnPointsTable();

        //then:
        assertTrue(editDataSourceWithPointListPageSubject.containsObject(dataPointCreatedCriteria.getIdentifier()));
    }
}