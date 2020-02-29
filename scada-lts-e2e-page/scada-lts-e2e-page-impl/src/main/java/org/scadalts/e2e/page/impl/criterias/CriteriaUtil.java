package org.scadalts.e2e.page.impl.criterias;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CriteriaUtil {

    public static Map<DataSourceCriteria, List<DataPointCriteria>> createCriteriaStructure(DataSourcePointCriteria[] criteria) {
        return Stream.of(criteria)
                .collect(Collectors.groupingBy(DataSourcePointCriteria::getDataSource,
                        Collectors.mapping(DataSourcePointCriteria::getDataPoint, Collectors.toList())));
    }

    public static Map<DataSourceCriteria, List<DataPointCriteria>> createCriteriaStructure(DataPointCriteria... dataPointCriteria) {
        Map<DataSourceCriteria, List<DataPointCriteria>> criteriaMap = new HashMap<>();
        criteriaMap.put(DataSourceCriteria.virtualDataSourceSecond(), Arrays.asList(dataPointCriteria.length == 0 ?
                new DataPointCriteria[]{DataPointCriteria.numericNoChange(4321)} : dataPointCriteria));
        return criteriaMap;
    }

    public static Map<DataSourceCriteria, List<DataPointCriteria>> createCriteriaStructure(DataSourceCriteria dataSourceCriteria, DataPointCriteria... dataPointCriteria) {
        Map<DataSourceCriteria, List<DataPointCriteria>> criteriaMap = new HashMap<>();
        criteriaMap.put(dataSourceCriteria, Arrays.asList(dataPointCriteria));
        return criteriaMap;
    }
}
