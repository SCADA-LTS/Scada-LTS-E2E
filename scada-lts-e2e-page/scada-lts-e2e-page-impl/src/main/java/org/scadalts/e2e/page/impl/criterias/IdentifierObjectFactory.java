package org.scadalts.e2e.page.impl.criterias;

import org.scadalts.e2e.page.impl.criterias.identifiers.*;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;

import java.util.Random;

public class IdentifierObjectFactory {

    public static DataPointIdentifier dataPointName(DataPointType dataPointType) {
        return new DataPointIdentifier("dp_test_" + unique(),dataPointType);
    }

    public static DataPointIdentifier dataPointAlarmName(DataPointType dataPointType) {
        return new DataPointIdentifier("Te AL Test_" + unique(),dataPointType);
    }

    public static DataPointIdentifier dataPointStorungName(DataPointType dataPointType) {
        return new DataPointIdentifier("Te ST Test_" + unique(),dataPointType);
    }

    public static DataPointIdentifier dataPointDeleteName(DataPointType dataPointType) {
        return new DataPointIdentifier("dp_testDelete_" + unique(),dataPointType);
    }

    public static DataPointIdentifier dataPointTargetName(DataPointType dataPointType) {
        return new DataPointIdentifier("dp_testTarget_" + unique(), dataPointType);
    }

    public static DataPointIdentifier dataPointSourceName(DataPointType dataPointType) {
        return new DataPointIdentifier("dp_testSource_" + unique(), dataPointType);
    }

    public static DataSourceIdentifier dataSourceName(DataSourceType dataSourceType) {
        return new DataSourceIdentifier("ds_test_" + unique(), dataSourceType);
    }

    public static DataSourceIdentifier dataSourceTargetName(DataSourceType dataSourceType) {
        return new DataSourceIdentifier("ds_testTarget_" + unique(), dataSourceType);
    }

    public static DataSourceIdentifier dataSourceSourceName(DataSourceType dataSourceType) {
        return new DataSourceIdentifier("ds_testSource_" + unique(), dataSourceType);
    }

    public static DataSourceIdentifier dataSourceDeleteName(DataSourceType dataSourceType) {
        return new DataSourceIdentifier("ds_testDelete_" + unique(), dataSourceType);
    }

    public static GraphicalViewIdentifier viewName() {
        return new GraphicalViewIdentifier("vw_test_" + unique());
    }

    public static EventDetectorIdentifier eventDetectorName(EventDetectorType eventDetectorType) {
        return new EventDetectorIdentifier("ed_test_" + unique(), eventDetectorType);
    }

    public static VarIdentifier varName() {
        return new VarIdentifier("vr_test_" + unique());
    }

    public static EventHandlerIdentifier eventHandlerName(EventHandlerType eventHandlerType) {
        return new EventHandlerIdentifier("eh_test_" + unique(), eventHandlerType);
    }

    public static ScriptIdentifier scriptName() {
        return new ScriptIdentifier("sc_test_" + unique());
    }

    public static WatchListIdentifier watchListName() {
        return new WatchListIdentifier("wl_test_" + unique());
    }
    public static String unique() {
        return String.valueOf(new Random().nextInt(9999999));
    }
}
