package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.criterias.RangeValue;
import org.scadalts.e2e.page.impl.dicts.*;
import org.scadalts.e2e.service.impl.services.datapoint.RangeValueJson;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Converter {
/*
    public static LoggingType convert(LoggingTypeJson loggingTypeJson) {
        if(loggingTypeJson == null)
            return LoggingType.getType("");
        return LoggingType.getType(loggingTypeJson.getName());
    }

    public static IntervalLoggingPeriodType convert(IntervalLoggingPeriodTypeJson intervalLoggingPeriodTypeJson) {
        if(intervalLoggingPeriodTypeJson == null)
            return IntervalLoggingPeriodType.getType("");
        return IntervalLoggingPeriodType.getType(intervalLoggingPeriodTypeJson.getName());
    }

    public static IntervalLoggingType convert(IntervalLoggingTypeJson intervalLoggingTypeJson) {
        if(intervalLoggingTypeJson == null)
            return IntervalLoggingType.getType("");
        return IntervalLoggingType.getType(intervalLoggingTypeJson.getName());
    }

    public static PurgeType convert(PurgeTypeJson purgeTypeJson) {
        if(purgeTypeJson == null)
            return PurgeType.getType("");
        return PurgeType.getType(purgeTypeJson.getName());
    }

    public static DataPointType covnert(DataPointTypeJson dataPointTypeJson) {
        if(dataPointTypeJson == null)
            return DataPointType.getType("");
        return DataPointType.getType(dataPointTypeJson.getName());
    }

    public static ChartRendererType convert(ChartRendererTypeJson chartRendererTypeJson) {
        if(chartRendererTypeJson == null)
            return ChartRendererType.getType("");
        return ChartRendererType.getType(chartRendererTypeJson.getName());
    }

    public static PeriodType convert(PeriodTypeJson periodTypeJson) {
        if(periodTypeJson == null)
            return PeriodType.getType("");
        return PeriodType.getType(periodTypeJson.getName());
    }

    public static DataPointType convert(DataPointTypeJson dataPointTypeJson) {
        if(dataPointTypeJson == null)
            return DataPointType.getType("");
        return DataPointType.getType(dataPointTypeJson.getName());
    }

    public static TextRendererType convert(TextRendererTypeJson textRendererTypeJson) {
        if(textRendererTypeJson == null)
            return TextRendererType.getType("");
        return TextRendererType.getType(textRendererTypeJson.getName());
    }

    public static Color convert(ColorJson colorJson) {
        if(colorJson == null)
            return Color.WHITE;
        return Color.valueOf(colorJson.getName());
    }
*/
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
