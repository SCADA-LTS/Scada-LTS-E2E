package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint.eventdetectors;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.PropertiesDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.EventDetectorObjectsCreator;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(E2eTestRunner.class)
public class CreateEventDetectorPageTest {

    private static DataSourcesPage dataSourcesPage;
    private static NavigationPage navigationPage;
    private static DataSourcePointObjectsCreator dataSourcePointObjectsCreator;

    private static DataSourceCriteria dataSourceCriteria;
    private static DataPointCriteria dataPointCriteria;
    private static EventDetectorCriteria eventDetectorCriteria;
    private static DataSourcePointCriteria dataSourcePointCriteria;

    @BeforeClass
    public static void createDataSourcePoint() {
        navigationPage = E2eAbstractRunnable.getNavigationPage();
        dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataPointCriteria = DataPointCriteria.binaryAlternate();
        dataSourcePointCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);

        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcesPage = dataSourcePointObjectsCreator.createObjects();
    }

    @After
    public void clean() {
        EventDetectorObjectsCreator eventDetectorObjectsCreator = new EventDetectorObjectsCreator(navigationPage,eventDetectorCriteria);
        eventDetectorObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_create_event_detector() {

        //given:
        EventDetectorType eventDetectorTypeExpected = EventDetectorType.CHANGE;
        EventDetectorIdentifier eventDetectorIdentifierExpected = new EventDetectorIdentifier("eventdetector_test_create");
        Xid xidExpected = Xid.xidForEventDetector();
        AlarmLevel alarmLevelExpected = AlarmLevel.INFORMATION;
        eventDetectorCriteria = EventDetectorCriteria.criteria(eventDetectorIdentifierExpected,
                eventDetectorTypeExpected,alarmLevelExpected,dataSourcePointCriteria);

        PropertiesDataPointPage page = dataSourcesPage
                .openDataSourceEditor(dataSourceCriteria)
                .openDataPointProperties(dataPointCriteria);

        //when:
        EditDataSourceWithPointListPage editDataSourcePage = page.selectEventDetectorType(eventDetectorTypeExpected)
                .addEventDetector()
                .setAlias(eventDetectorIdentifierExpected)
                .setXid(xidExpected)
                .selectAlarmLevel(alarmLevelExpected)
                .saveDataPoint()
                .waitOnEventDetectorTable()
                .editDataSource();

        //and:
        page = editDataSourcePage
                .openDataPointProperties(dataPointCriteria);

        AlarmLevel alarmLevel = page.getAlarmLevelFirst();
        Xid xid = page.getXidFirst();
        EventDetectorIdentifier eventDetectorIdentifier = page.getAliasFirst();

        //then:
        assertThat(true, equalTo(page.containsObject(eventDetectorCriteria)));
        assertThat(alarmLevel, equalTo(alarmLevelExpected));
        assertThat(xid, equalTo(xidExpected));
        assertThat(eventDetectorIdentifier, equalTo(eventDetectorIdentifierExpected));
    }

}
