package org.scadalts.e2e.page.impl.criterias;

import org.scadalts.e2e.page.impl.criterias.identifiers.*;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;

public class IdentifierObjectFactory {

    public static DataPointIdentifier dataPointName(DataPointType dataPointType) {
        return new DataPointIdentifier("dp_test" + System.nanoTime(),dataPointType);
    }

    public static DataPointIdentifier dataPointTargetName(DataPointType dataPointType) {
        return new DataPointIdentifier("dp_testTarget" + System.nanoTime(), dataPointType);
    }

    public static DataPointIdentifier dataPointSourceName(DataPointType dataPointType) {
        return new DataPointIdentifier("dp_testSource" + System.nanoTime(), dataPointType);
    }

    public static DataSourceIdentifier dataSourceName(DataSourceType dataSourceType) {
        return new DataSourceIdentifier("ds_test" + System.nanoTime(), dataSourceType);
    }

    public static DataSourceIdentifier dataSourceTargetName(DataSourceType dataSourceType) {
        return new DataSourceIdentifier("ds_testTarget" + System.nanoTime(), dataSourceType);
    }

    public static DataSourceIdentifier dataSourceSourceName(DataSourceType dataSourceType) {
        return new DataSourceIdentifier("ds_testSource" + System.nanoTime(), dataSourceType);
    }

    public static GraphicalViewIdentifier viewName() {
        return new GraphicalViewIdentifier("view_test" + System.nanoTime());
    }

    public static EventDetectorIdentifier eventDetectorName(EventDetectorType eventDetectorType) {
        return new EventDetectorIdentifier("ed_test" + System.nanoTime(), eventDetectorType);
    }

    public static VarIdentifier varName() {
        return new VarIdentifier("vr_test" + System.nanoTime());
    }

    public static EventHandlerIdentifier eventHandlerName(EventHandlerType eventHandlerType) {
        return new EventHandlerIdentifier("eh_test" + System.nanoTime(), eventHandlerType);
    }

    public static ScriptIdentifier scriptName() {
        return new ScriptIdentifier("sc_test" + System.nanoTime());
    }

}
