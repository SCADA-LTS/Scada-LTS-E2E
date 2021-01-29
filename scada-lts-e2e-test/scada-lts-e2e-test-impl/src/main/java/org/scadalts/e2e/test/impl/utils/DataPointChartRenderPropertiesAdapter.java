package org.scadalts.e2e.test.impl.utils;

import lombok.EqualsAndHashCode;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointChartRenderProperties;
import org.scadalts.e2e.page.impl.dicts.ChartRendererType;
import org.scadalts.e2e.page.impl.dicts.PeriodType;
import org.scadalts.e2e.service.impl.services.datapoint.ChartRenderer;

@EqualsAndHashCode(callSuper = true)
public class DataPointChartRenderPropertiesAdapter extends DataPointChartRenderProperties {
    public DataPointChartRenderPropertiesAdapter(ChartRenderer chartRenderer) {
        super(ChartRendererType.getType(chartRenderer.getType()), PeriodType.getType(chartRenderer.getTimePeriodType()),
                chartRenderer.isIncludeSum(), chartRenderer.getNumberOfPeriods(), chartRenderer.getLimit());
    }
}
