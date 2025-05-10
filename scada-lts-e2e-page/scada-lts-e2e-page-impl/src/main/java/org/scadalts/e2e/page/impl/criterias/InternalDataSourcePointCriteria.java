package org.scadalts.e2e.page.impl.criterias;

import lombok.*;


@Getter
@ToString
public class InternalDataSourcePointCriteria extends DataSourcePointCriteria<UpdateDataSourceCriteria, InternalDataPointCriteria> {

    public InternalDataSourcePointCriteria(@NonNull UpdateDataSourceCriteria dataSource, @NonNull InternalDataPointCriteria dataPoint) {
        super(dataSource, dataPoint);
    }

    public static InternalDataSourcePointCriteria empty() {
        return new InternalDataSourcePointCriteria(UpdateDataSourceCriteria.empty(),
                InternalDataPointCriteria.empty());
    }

    public static InternalDataSourcePointCriteria maximumThreadStackHeight(UpdateDataSourceCriteria dataSourceCriteria, String dataPointXid) {
        return new InternalDataSourcePointCriteria(dataSourceCriteria, InternalDataPointCriteria.maximumThreadStackHeight(dataPointXid));
    }

    public static InternalDataSourcePointCriteria pointValuesToBeWritten(UpdateDataSourceCriteria dataSourceCriteria, String dataPointXid) {
        return new InternalDataSourcePointCriteria(dataSourceCriteria, InternalDataPointCriteria.pointValuesToBeWritten(dataPointXid));
    }

    public static InternalDataSourcePointCriteria pointValueWriteThreads(UpdateDataSourceCriteria dataSourceCriteria, String dataPointXid) {
        return new InternalDataSourcePointCriteria(dataSourceCriteria, InternalDataPointCriteria.pointValueWriteThreads(dataPointXid));
    }

    public static InternalDataSourcePointCriteria activeThreadCount(UpdateDataSourceCriteria dataSourceCriteria, String dataPointXid) {
        return new InternalDataSourcePointCriteria(dataSourceCriteria, InternalDataPointCriteria.activeThreadCount(dataPointXid));
    }

    public static InternalDataSourcePointCriteria highPriorityWorkItems(UpdateDataSourceCriteria dataSourceCriteria, String dataPointXid) {
        return new InternalDataSourcePointCriteria(dataSourceCriteria, InternalDataPointCriteria.highPriorityWorkItems(dataPointXid));
    }

    public static InternalDataSourcePointCriteria mediumPriorityWorkItems(UpdateDataSourceCriteria dataSourceCriteria, String dataPointXid) {
        return new InternalDataSourcePointCriteria(dataSourceCriteria, InternalDataPointCriteria.mediumPriorityWorkItems(dataPointXid));
    }

    public static InternalDataSourcePointCriteria scheduledWorkItems(UpdateDataSourceCriteria dataSourceCriteria, String dataPointXid) {
        return new InternalDataSourcePointCriteria(dataSourceCriteria, InternalDataPointCriteria.scheduledWorkItems(dataPointXid));
    }

    public static InternalDataSourcePointCriteria criteria(UpdateDataSourceCriteria dataSourceCriteria,
                                                           InternalDataPointCriteria dataPointCriteria) {
        return new InternalDataSourcePointCriteria(dataSourceCriteria, dataPointCriteria);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
