package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.eventdetectors;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.EventDetectorObjectsCreator;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.scadalts.e2e.test.impl.matchers.ContainsObject.containsObject;

public class CreateEventDetectorPageTest {

    private static DataSourcesPage dataSourcesPage;
    private static NavigationPage navigationPage;
    private static DataSourcePointObjectsCreator dataSourcePointObjectsCreator;

    private static UpdateDataSourceCriteria dataSourceCriteria;
    private static VirtualDataPointCriteria dataPointCriteria;
    private static VirtualDataSourcePointCriteria dataSourcePointCriteria;
    private EventDetectorCriteria eventDetectorCriteria;

    @BeforeClass
    public static void createDataSourcePoint() {
        navigationPage = TestWithPageUtil.openNavigationPage();
        dataSourceCriteria = UpdateDataSourceCriteria.virtualDataSourceSecond();
        dataPointCriteria = VirtualDataPointCriteria.binaryNoChange();
        dataSourcePointCriteria = VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointCriteria);

        dataSourcePointObjectsCreator = new VirtualDataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcesPage = dataSourcePointObjectsCreator.createObjects();
    }

    @After
    public void clean() {
        EventDetectorObjectsCreator eventDetectorObjectsCreator = new EventDetectorObjectsCreator(navigationPage,eventDetectorCriteria);
        eventDetectorObjectsCreator.deleteObjects();
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_create_event_detector() {

        //given:
        EventDetectorType eventDetectorTypeExpected = EventDetectorType.CHANGE;
        EventDetectorIdentifier eventDetectorIdentifierExpected = IdentifierObjectFactory.eventDetectorName(eventDetectorTypeExpected);
        Xid xidExpected = Xid.eventDetector();
        AlarmLevel alarmLevelExpected = AlarmLevel.INFORMATION;
        eventDetectorCriteria = EventDetectorCriteria.criteria(eventDetectorIdentifierExpected,
                alarmLevelExpected,dataSourcePointCriteria);

        DataPointPropertiesPage page = dataSourcesPage
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .openDataPointProperties(dataPointCriteria.getIdentifier());

        //when:
        EditDataSourceWithPointListPage editDataSourcePage = page.selectEventDetectorType(eventDetectorTypeExpected)
                .addEventDetector()
                .setEventDetectorAlias(eventDetectorIdentifierExpected)
                .setEventDetectorXid(xidExpected)
                .selectEventDetectorAlarmLevel(alarmLevelExpected)
                .saveDataPoint()
                .waitOnPageWhileNotVisible(eventDetectorCriteria.getIdentifier())
                .editDataSource();

        //and:
        page = editDataSourcePage
                .openDataPointProperties(dataPointCriteria.getIdentifier());

        AlarmLevel alarmLevel = page.getEventDetectorAlarmLevelFirst();
        Xid xid = page.getEventDetectorXidFirst();
        EventDetectorIdentifier eventDetectorIdentifier = page.getEventDetectorAliasFirst();

        //then:
        assertThat(page, containsObject(eventDetectorCriteria.getIdentifier()));
        assertThat(alarmLevel, equalTo(alarmLevelExpected));
        assertThat(xid, equalTo(xidExpected));
        assertThat(eventDetectorIdentifier, equalTo(eventDetectorIdentifierExpected));
    }

}
