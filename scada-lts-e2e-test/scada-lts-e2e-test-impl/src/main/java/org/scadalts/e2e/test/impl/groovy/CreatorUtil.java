package org.scadalts.e2e.test.impl.groovy;

import org.scadalts.e2e.page.core.utils.AlertUtil;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;
import org.scadalts.e2e.page.impl.pages.eventhandlers.EventHandlersPage;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.page.impl.pages.scripts.ScriptsPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.*;

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
        DataSourcePointObjectsCreator creator =
                new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, dataPointCriteria);
        creators.add(creator);
        return creator.createObjects();
    }

    public static DataSourcesPage create(DataSourceCriteria criteria) {
        DataSourcePointObjectsCreator creator =
                new DataSourcePointObjectsCreator(navigationPage, criteria);
        creators.add(creator);
        return creator.createObjects();
    }

    public static DataSourcesPage create(DataPointCriteria dataPointCriteria,
                                         DataSourceCriteria dataSourceCriteria) {
        return create(dataSourceCriteria, dataPointCriteria);
    }

    public static DataPointPropertiesPage create(EventDetectorCriteria criteria) {
        EventDetectorObjectsCreator creator =
                new EventDetectorObjectsCreator(navigationPage, criteria);
        creators.add(creator);
        return creator.createObjects();
    }

    public static GraphicalViewsPage create(GraphicalViewCriteria criteria) {
        GraphicalViewObjectsCreator creator =
                new GraphicalViewObjectsCreator(navigationPage, criteria);
        creators.add(creator);
        return creator.createObjects();
    }

    public static PointLinksPage create(PointLinkCriteria criteria) {
        PointLinksObjectsCreator creator =
                new PointLinksObjectsCreator(navigationPage, criteria);
        creators.add(creator);
        return creator.createObjects();
    }

    public static EventHandlersPage create(EventHandlerCriteria criteria) {
        EventHandlerObjectsCreator creator =
                new EventHandlerObjectsCreator(navigationPage, criteria);
        creators.add(creator);
        return creator.createObjects();
    }

    public static ScriptsPage create(ScriptCriteria criteria) {
        ScriptObjectsCreator creator =
                new ScriptObjectsCreator(navigationPage, criteria);
        creators.add(creator);
        return creator.createObjects();
    }

    public static DataSourcesPage create(DataSourcePointCriteria criteria) {
        DataSourcePointObjectsCreator creator =
                new DataSourcePointObjectsCreator(navigationPage, criteria);
        creators.add(creator);
        return creator.createObjects();
    }

    public static WatchListPage create(WatchListCriteria criteria) {
        WatchListObjectsCreator creator =
                new WatchListObjectsCreator(navigationPage, criteria);
        creators.add(creator);
        return creator.createObjects();
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
