package org.scadalts.e2e.page.impl.criterias;

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
}
