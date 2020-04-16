package org.scadalts.e2e.test.impl.creators;

import org.scadalts.e2e.page.core.pages.PageObject;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

public class CreatorObjectFactory {

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creator(NavigationPage navigationPage,
                                                                                                 DataSourceCriteria dataSourceCriteria) {
        return (CreatorObject<T, R>) new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria);
    }

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creator(NavigationPage navigationPage,
                                                                                                 DataSourcePointCriteria... dataSourcePointCriterias) {
        return (CreatorObject<T, R>) new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriterias);
    }

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creator(NavigationPage navigationPage,
                                                                                                 ScriptCriteria... scriptCriterias)  {
        return (CreatorObject<T, R>) new ScriptObjectsCreator(navigationPage, scriptCriterias);
    }

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creator(NavigationPage navigationPage,
                                                                                                 EventDetectorCriteria eventDetectorCriteria)  {
        return (CreatorObject<T, R>) new EventDetectorObjectsCreator(navigationPage, eventDetectorCriteria);
    }

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creator(NavigationPage navigationPage,
                                                                                                 EventHandlerCriteria... eventHandlerCriterias)  {
        return (CreatorObject<T, R>) new EventHandlerObjectsCreator(navigationPage, eventHandlerCriterias);
    }

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creator(NavigationPage navigationPage,
                                                                                                 GraphicalViewCriteria eventHandlerCriteria)  {
        return (CreatorObject<T, R>) new GraphicalViewObjectsCreator(navigationPage, eventHandlerCriteria);
    }

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creator(NavigationPage navigationPage,
                                                                                                 PointLinkCriteria eventHandlerCriteria)  {
        return (CreatorObject<T, R>) new PointLinksObjectsCreator(navigationPage, eventHandlerCriteria);
    }

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creatorWatchList(NavigationPage navigationPage,
                                                                                                 WatchListCriteria... dataSourcePointCriterias)  {
        return (CreatorObject<T, R>) new WatchListObjectsCreator(navigationPage, dataSourcePointCriterias);
    }
}
