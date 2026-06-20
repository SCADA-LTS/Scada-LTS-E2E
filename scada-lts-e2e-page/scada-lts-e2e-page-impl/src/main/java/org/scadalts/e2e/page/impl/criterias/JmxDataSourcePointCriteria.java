package org.scadalts.e2e.page.impl.criterias;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


@Getter
@ToString
public class JmxDataSourcePointCriteria extends DataSourcePointCriteria<JmxDataSourceCriteria, JmxDataPointCriteria> {

    public JmxDataSourcePointCriteria(@NonNull JmxDataSourceCriteria dataSource, @NonNull JmxDataPointCriteria dataPoint) {
        super(dataSource, dataPoint);
    }

    public static JmxDataSourcePointCriteria empty() {
        return new JmxDataSourcePointCriteria(JmxDataSourceCriteria.empty(),
                JmxDataPointCriteria.empty());
    }

    public static JmxDataSourcePointCriteria jdbcActive(JmxDataSourceCriteria dataSourceCriteria, String dataPointXid) {
        return new JmxDataSourcePointCriteria(dataSourceCriteria, JmxDataPointCriteria.jdbcActive(dataPointXid));
    }

    public static JmxDataSourcePointCriteria criteria(JmxDataSourceCriteria dataSourceCriteria,
                                                      JmxDataPointCriteria dataPointCriteria) {
        return new JmxDataSourcePointCriteria(dataSourceCriteria, dataPointCriteria);
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
