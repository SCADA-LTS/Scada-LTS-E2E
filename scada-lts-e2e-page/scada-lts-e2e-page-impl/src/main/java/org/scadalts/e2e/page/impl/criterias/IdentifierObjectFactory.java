package org.scadalts.e2e.page.impl.criterias;

import org.scadalts.e2e.page.impl.criterias.identifiers.*;

public class IdentifierObjectFactory {

    public static DataPointIdentifier dataPointName() {
        return new DataPointIdentifier("dp_test" + System.nanoTime());
    }

    public static DataPointIdentifier dataPointTargetName() {
        return new DataPointIdentifier("dp_testTarget" + System.nanoTime());
    }

    public static DataPointIdentifier dataPointSourceName() {
        return new DataPointIdentifier("dp_testSource" + System.nanoTime());
    }

    public static DataSourceIdentifier dataSourceName() {
        return new DataSourceIdentifier("ds_test" + System.nanoTime());
    }

    public static DataSourceIdentifier dataSourceTargetName() {
        return new DataSourceIdentifier("ds_testTarget" + System.nanoTime());
    }

    public static DataSourceIdentifier dataSourceSourceName() {
        return new DataSourceIdentifier("ds_testSource" + System.nanoTime());
    }

    public static GraphicalViewIdentifier viewName() {
        return new GraphicalViewIdentifier("view_test" + System.nanoTime());
    }

    public static EventDetectorIdentifier eventDetectorName() {
        return new EventDetectorIdentifier("ed_test" + System.nanoTime());
    }

    public static VarIdentifier varName() {
        return new VarIdentifier("vr_test" + System.nanoTime());
    }

    public static EventHandlerIdentifier eventHandlerName() {
        return new EventHandlerIdentifier("eh_test" + System.nanoTime());
    }

    public static ScriptIdentifier scriptName() {
        return new ScriptIdentifier("sc_test" + System.nanoTime());
    }

}
