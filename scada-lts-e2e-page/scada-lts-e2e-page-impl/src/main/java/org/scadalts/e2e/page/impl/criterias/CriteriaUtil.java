package org.scadalts.e2e.page.impl.criterias;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CriteriaUtil {

    public static <S extends DataSourceCriteria, P extends DataPointCriteria> Map<S, List<P>> createCriteriaStructure(DataSourcePointCriteria<S, P>... criteria) {
        return Stream.of(criteria)
                .collect(Collectors.groupingBy(DataSourcePointCriteria::getDataSource,
                        Collectors.mapping(DataSourcePointCriteria::getDataPoint, Collectors.toList())));
    }

    public static <S extends DataSourceCriteria, P extends DataPointCriteria> Map<S, List<P>> createCriteriaStructure(List<DataSourcePointCriteria<S, P>> criteria) {
        return criteria.stream()
                .collect(Collectors.groupingBy(DataSourcePointCriteria::getDataSource,
                        Collectors.mapping(DataSourcePointCriteria::getDataPoint, Collectors.toList())));
    }

    public static <S extends DataSourceCriteria, P extends DataPointCriteria> Map<S, List<P>> createCriteriaStructure(S dataSourceCriteria,
                                                                                                                      P... dataPointCriteria) {
        Map<S, List<P>> criteriaMap = new HashMap<>();
        criteriaMap.put(dataSourceCriteria, dataPointCriteria == null ? Collections.emptyList() : Arrays.asList(dataPointCriteria));
        return criteriaMap;
    }

    public static <S extends DataSourceCriteria, P extends DataPointCriteria> Map<S, List<P>> createCriteriaStructure(S dataSource,
                                                                                                                      List<P> dataPoints) {
        Map<S, List<P>> criteriaMap = new HashMap<>();
        criteriaMap.put(dataSource, dataPoints == null ? Collections.emptyList() : dataPoints);
        return criteriaMap;
    }
}
