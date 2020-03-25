package org.scadalts.e2e.test.impl.creators;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.PropertiesDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

@Log4j2
public class EventDetectorObjectsCreator implements CreatorObject<PropertiesDataPointPage, PropertiesDataPointPage> {

    private final NavigationPage navigationPage;
    private DataSourcesPage dataSourcesPage;
    private final EventDetectorCriteria eventDetectorCriteria;

    public EventDetectorObjectsCreator(NavigationPage navigationPage, EventDetectorCriteria eventDetectorCriteria) {
        this.navigationPage = navigationPage;
        this.eventDetectorCriteria = eventDetectorCriteria;
    }

    @Override
    public PropertiesDataPointPage deleteObjects() {
        PropertiesDataPointPage propertiesDataPointPage = openPage();
        if(propertiesDataPointPage.containsObject(eventDetectorCriteria.getIdentifier())) {
            logger.info("delete object: {}, type: {}, xid: {}, class: {}", eventDetectorCriteria.getIdentifier().getValue(),
                    eventDetectorCriteria.getIdentifier().getType(), eventDetectorCriteria.getXid().getValue(),
                    eventDetectorCriteria.getClass().getSimpleName());
            propertiesDataPointPage.deleteEventDetector(eventDetectorCriteria)
                    .saveDataPoint();
        }
        return propertiesDataPointPage;
    }

    @Override
    public PropertiesDataPointPage createObjects() {
        PropertiesDataPointPage propertiesDataPointPage = openPage();
        if(!propertiesDataPointPage.containsObject(eventDetectorCriteria.getIdentifier())) {
            logger.info("create object: {}, type: {}, xid: {}, class: {}", eventDetectorCriteria.getIdentifier().getValue(),
                    eventDetectorCriteria.getIdentifier().getType(), eventDetectorCriteria.getXid().getValue(),
                    eventDetectorCriteria.getClass().getSimpleName());
            propertiesDataPointPage.selectEventDetectorType(eventDetectorCriteria.getIdentifier().getType())
                    .addEventDetector()
                    .setAlias(eventDetectorCriteria.getIdentifier())
                    .setXid(eventDetectorCriteria.getXid())
                    .selectAlarmLevel(eventDetectorCriteria.getAlarmLevel())
                    .saveDataPoint();

        }
        return propertiesDataPointPage;
    }

    @Override
    public PropertiesDataPointPage openPage() {
        DataSourcePointCriteria dataSourcePointCriteria = eventDetectorCriteria.getDataSourcePointCriteria();
        DataSourceCriteria dataSourceCriteria = dataSourcePointCriteria.getDataSource();
        DataPointCriteria dataPointCriteria = dataSourcePointCriteria.getDataPoint();
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage.openDataSourceEditor(dataSourceCriteria.getIdentifier())
                    .openDataPointProperties(dataPointCriteria.getIdentifier());
        }
        return dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .openDataPointProperties(dataPointCriteria.getIdentifier());
    }
}
