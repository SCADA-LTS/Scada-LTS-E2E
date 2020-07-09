package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.RangeValue;
import org.scadalts.e2e.service.impl.services.datapoint.*;

import java.util.List;
import java.util.stream.Collectors;

public class DataPointPropertiesJsonAdapter extends DataPointPropertiesJson {

    private final DataSourcePointCriteria dataSourcePointCriteria;

    public DataPointPropertiesJsonAdapter(DataSourcePointCriteria dataPointCriteria) {
        this.dataSourcePointCriteria = dataPointCriteria;
    }

    private static List<RangeValueJson> _from(List<RangeValue> rangeValues) {
        return rangeValues.stream()
                .map(a -> RangeValueJson.builder()
                .colour(a.getColour().getCode())
                .from(a.getFrom())
                .to(a.getTo())
                .text(a.getText())
                .build()).collect(Collectors.toList());
    }
}
