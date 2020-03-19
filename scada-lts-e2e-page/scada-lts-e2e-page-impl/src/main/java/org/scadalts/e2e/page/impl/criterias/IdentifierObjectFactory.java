package org.scadalts.e2e.page.impl.criterias;

import org.scadalts.e2e.page.impl.criterias.identifiers.*;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;

import java.util.Random;

public class IdentifierObjectFactory {

    public static DataPointIdentifier dataPointName(DataPointType dataPointType) {
        return new DataPointIdentifier("dp_test" + unique(),dataPointType);
    }

    public static DataPointIdentifier dataPointTargetName(DataPointType dataPointType) {
        return new DataPointIdentifier("dp_testTarget" + unique(), dataPointType);
    }

    public static DataPointIdentifier dataPointSourceName(DataPointType dataPointType) {
        return new DataPointIdentifier("dp_testSource" + unique(), dataPointType);
    }

    public static DataSourceIdentifier dataSourceName(DataSourceType dataSourceType) {
        return new DataSourceIdentifier("ds_test" + unique(), dataSourceType);
    }

    public static DataSourceIdentifier dataSourceTargetName(DataSourceType dataSourceType) {
        return new DataSourceIdentifier("ds_testTarget" + unique(), dataSourceType);
    }

    public static DataSourceIdentifier dataSourceSourceName(DataSourceType dataSourceType) {
        return new DataSourceIdentifier("ds_testSource" + unique(), dataSourceType);
    }

    public static GraphicalViewIdentifier viewName() {
        return new GraphicalViewIdentifier("vw_test" + unique());
    }

    public static EventDetectorIdentifier eventDetectorName(EventDetectorType eventDetectorType) {
        return new EventDetectorIdentifier("ed_test" + unique(), eventDetectorType);
    }

    public static VarIdentifier varName() {
        return new VarIdentifier("vr_test" + unique());
    }

    public static EventHandlerIdentifier eventHandlerName(EventHandlerType eventHandlerType) {
        return new EventHandlerIdentifier("eh_test" + unique(), eventHandlerType);
    }

    public static ScriptIdentifier scriptName() {
        return new ScriptIdentifier("sc_test" + unique());
    }

    public static String unique() {
        return String.valueOf(new Random().nextInt(9999));
    }
}
