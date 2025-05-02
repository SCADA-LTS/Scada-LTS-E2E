package org.scadalts.e2e.test.impl.creators;

import org.scadalts.e2e.page.core.pages.PageObject;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreatorObjectFactory {

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creator(NavigationPage navigationPage, DataSourcePointCriteria<?, ?>... dataSourcePoints) {
        if(navigationPage == null || dataSourcePoints == null || dataSourcePoints.length == 0) {
            throw new IllegalArgumentException();
        }
        if(dataSourcePoints[0].getDataPoint() instanceof VirtualDataPointCriteria && dataSourcePoints[0].getDataSource() instanceof UpdateDataSourceCriteria) {
            return (CreatorObject<T, R>) Stream.of(dataSourcePoints).collect(Collectors
                            .groupingBy(a -> (UpdateDataSourceCriteria) a.getDataSource(), Collectors.mapping(a -> (VirtualDataPointCriteria) a.getDataPoint(), Collectors.toList())))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(a -> a.getKey(), b -> new VirtualDataPointObjectsCreator(navigationPage, b.getKey(), b.getValue())));
        }

        if(dataSourcePoints[0].getDataPoint() instanceof InternalDataPointCriteria && dataSourcePoints[0].getDataSource() instanceof UpdateDataSourceCriteria) {
            return (CreatorObject<T, R>) Stream.of(dataSourcePoints).collect(Collectors
                            .groupingBy(a -> (UpdateDataSourceCriteria) a.getDataSource(), Collectors.mapping(a -> (InternalDataPointCriteria) a.getDataPoint(), Collectors.toList())))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(a -> a.getKey(), b -> new InternalDataPointObjectsCreator(navigationPage, b.getKey(), b.getValue())));
        }
        throw new UnsupportedOperationException("");
    }

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creator(NavigationPage navigationPage,
                                                                                                 ScriptCriteria... scriptCriterias)  {
        return (CreatorObject<T, R>) new ScriptObjectsCreator(navigationPage, scriptCriterias);
    }

    public static <T extends PageObject<T>, R extends PageObject<R>> CreatorObject<T, R> creator(NavigationPage navigationPage,
                                                                                                 EventDetectorCriteria eventDetectorCriteria,
                                                                                                 DataSourcePointObjectsCreator<DataSourceCriteria, DataPointCriteria> dataSourcePointObjectsCreator)  {
        return (CreatorObject<T, R>) new EventDetectorObjectsCreator(navigationPage, eventDetectorCriteria, dataSourcePointObjectsCreator);
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
