package org.scadalts.e2e.page.impl.criterias.properties;

import lombok.*;
import org.scadalts.e2e.page.impl.dicts.ChartRendererType;
import org.scadalts.e2e.page.impl.dicts.PeriodType;

import java.util.Objects;

@Data
@Getter
@Builder
public class DataPointChartRenderProperties {

    private final ChartRendererType chartRendererType;
    private final PeriodType periodType;
    private final boolean includeSum;
    private final int period;
    private final int limit;

    private static final DataPointChartRenderProperties EMPTY = none();

    public DataPointChartRenderProperties(ChartRendererType chartRendererType, PeriodType periodType,
                                          boolean includeSum, int period, int limit) {
        this.chartRendererType = Objects.isNull(chartRendererType) ? ChartRendererType.NONE : chartRendererType;
        this.periodType = Objects.isNull(periodType) ? PeriodType.MINUTES : periodType;
        this.includeSum = includeSum;
        this.period = period;
        this.limit = limit;
    }

    public static DataPointChartRenderProperties empty() {
        return EMPTY;
    }

    public static DataPointChartRenderProperties none() {
        return DataPointChartRenderProperties.builder()
                .chartRendererType(ChartRendererType.NONE)
                .includeSum(false)
                .period(0)
                .periodType(PeriodType.MINUTES)
                .limit(0)
                .build();
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataPointChartRenderProperties)) return false;
        DataPointChartRenderProperties that = (DataPointChartRenderProperties) o;
        return isIncludeSum() == that.isIncludeSum() &&
                getPeriod() == that.getPeriod() &&
                getLimit() == that.getLimit() &&
                getChartRendererType() == that.getChartRendererType() &&
                getPeriodType() == that.getPeriodType();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getChartRendererType(), getPeriodType(), isIncludeSum(), getPeriod(), getLimit());
    }
}
