package org.scadalts.e2e.test.impl.groovy;

import org.scadalts.e2e.page.core.utils.AlertUtil;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.EventDetectorObjectsCreator;
import org.scadalts.e2e.test.impl.creators.GraphicalViewObjectsCreator;

import java.util.ArrayList;
import java.util.List;

public class CreatorUtil {

    private static List<CreatorObject> creators = new ArrayList<>();

    private static NavigationPage navigationPage;

    public static void init(NavigationPage navigation) {
        navigationPage = navigation;
    }

    public static DataSourcesPage create(DataSourceCriteria dataSourceCriteria,
                                         DataPointCriteria dataPointCriteria) {
        DataSourcePointObjectsCreator dataSourcePointObjectsCreator =
                new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, dataPointCriteria);
        creators.add(dataSourcePointObjectsCreator);
        return dataSourcePointObjectsCreator.createObjects();
    }

    public static DataSourcesPage create(DataPointCriteria dataPointCriteria,
                                         DataSourceCriteria dataSourceCriteria) {
        return create(dataSourceCriteria, dataPointCriteria);
    }

    public static DataPointPropertiesPage create(EventDetectorCriteria eventDetectorCriteria) {
        EventDetectorObjectsCreator eventDetectorObjectsCreator =
                new EventDetectorObjectsCreator(navigationPage, eventDetectorCriteria);
        creators.add(eventDetectorObjectsCreator);
        return eventDetectorObjectsCreator.createObjects();
    }

    public static GraphicalViewsPage create(GraphicalViewCriteria graphicalViewCriteria) {
        GraphicalViewObjectsCreator graphicalViewObjectsCreator =
                new GraphicalViewObjectsCreator(navigationPage, graphicalViewCriteria);
        creators.add(graphicalViewObjectsCreator);
        return graphicalViewObjectsCreator.createObjects();
    }

    public static void deleteObjects() {
        if(!creators.isEmpty()) {
            AlertUtil.acceptAlert();
            for (CreatorObject creatorObject : creators) {
                creatorObject.deleteObjects();
            }
        }
    }
}
