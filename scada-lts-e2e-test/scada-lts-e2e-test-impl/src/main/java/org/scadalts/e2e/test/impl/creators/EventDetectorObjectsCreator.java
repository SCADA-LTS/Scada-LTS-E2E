package org.scadalts.e2e.test.impl.creators;

import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.PropertiesDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

public class EventDetectorObjectsCreator implements CreatorObject<PropertiesDataPointPage, PropertiesDataPointPage> {

    private final NavigationPage navigationPage;
    private DataSourcesPage dataSourcesPage;
    private final EventDetectorCriteria eventHandlerCriteria;

    public EventDetectorObjectsCreator(NavigationPage navigationPage, EventDetectorCriteria eventHandlerCriteria) {
        this.navigationPage = navigationPage;
        this.eventHandlerCriteria = eventHandlerCriteria;
    }

    @Override
    public PropertiesDataPointPage deleteObjects() {
        PropertiesDataPointPage propertiesDataPointPage = openPage();
        if(propertiesDataPointPage.containsObject(eventHandlerCriteria)) {
            propertiesDataPointPage.deleteEventDetector(eventHandlerCriteria)
                    .saveDataPoint();
        }
        return propertiesDataPointPage;
    }

    @Override
    public PropertiesDataPointPage createObjects() {
        PropertiesDataPointPage propertiesDataPointPage = openPage();
        if(!propertiesDataPointPage.containsObject(eventHandlerCriteria)) {
            propertiesDataPointPage.selectEventDetectorType(eventHandlerCriteria.getType())
                    .addEventDetector()
                    .setAlias(eventHandlerCriteria.getIdentifier())
                    .setXid(eventHandlerCriteria.getXid())
                    .selectAlarmLevel(eventHandlerCriteria.getAlarmLevel())
                    .saveDataPoint();
        }
        return propertiesDataPointPage;
    }

    @Override
    public PropertiesDataPointPage openPage() {
        DataSourcePointCriteria dataSourcePointCriteria = eventHandlerCriteria.getDataSourcePointCriteria();
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage.openDataSourceEditor(dataSourcePointCriteria.getDataSource())
                    .openDataPointProperties(dataSourcePointCriteria.getDataPoint());
        }
        return dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourcePointCriteria.getDataSource())
                .openDataPointProperties(dataSourcePointCriteria.getDataPoint());
    }
}
