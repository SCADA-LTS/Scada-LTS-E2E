package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.criterias.RangeValue;
import org.scadalts.e2e.page.impl.dicts.*;
import org.scadalts.e2e.service.impl.services.datapoint.RangeValueJson;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Converter {

    public static List<RangeValue> convert(List<RangeValueJson> rangeValueJsons) {
        if(Objects.isNull(rangeValueJsons))
            return Collections.emptyList();
        return rangeValueJsons.stream().map(a -> RangeValue.builder()
        .colour(Color.getType(a.getColour()))
        .from(a.getFrom())
        .to(a.getTo())
        .text(a.getText())
        .build()).collect(Collectors.toList());
    }
}
