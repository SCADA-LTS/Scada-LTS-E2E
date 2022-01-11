package org.scadalts.e2e.page.impl.groovy;

import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.*;
import org.scadalts.e2e.page.impl.dicts.*;

public class NameUtil {

    public static DataPointIdentifier dataPointName(DataPointType dataPointType) {
        return IdentifierObjectFactory.dataPointName(dataPointType);
    }

    public static DataPointIdentifier dataPointName() {
        return IdentifierObjectFactory.dataPointName(DataPointType.BINARY);
    }

    public static DataPointIdentifier dataPointAlarmName() {
        return IdentifierObjectFactory.dataPointAlarmBinaryTypeName();
    }

    public static DataPointIdentifier dataPointStorungName() {
        return IdentifierObjectFactory.dataPointStorungBinaryTypeName();
    }

    public static DataSourceIdentifier dataSourceName() {
        return IdentifierObjectFactory.dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE);
    }

    public static DataSourceIdentifier dataSourceName(DataSourceType dataSourceType) {
        return IdentifierObjectFactory.dataSourceName(dataSourceType);
    }

    public static GraphicalViewIdentifier viewName() {
        return IdentifierObjectFactory.viewName();
    }

    public static EventDetectorIdentifier eventDetectorName() {
        return IdentifierObjectFactory.eventDetectorName(EventDetectorType.NO_CHANGE);
    }

    public static EventDetectorIdentifier eventDetectorName(EventDetectorType eventDetectorType) {
        return IdentifierObjectFactory.eventDetectorName(eventDetectorType);
    }

    public static VarIdentifier varName() {
        return IdentifierObjectFactory.varName();
    }

    public static EventHandlerIdentifier eventHandlerName(EventHandlerType eventHandlerType) {
        return IdentifierObjectFactory.eventHandlerName(eventHandlerType);
    }

    public static ScriptIdentifier scriptName() {
        return IdentifierObjectFactory.scriptName();
    }

    public static WatchListIdentifier watchListName() {
        return IdentifierObjectFactory.watchListName();
    }
}
