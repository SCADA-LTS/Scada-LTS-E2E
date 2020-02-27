package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criteria.SourcePointCriteria;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestsUtil {

    public static Map<DataSourceCriteria, DataPointCriteria[]> createStructure(SourcePointCriteria[] criteria) {
        return Stream.of(criteria)
                .collect(Collectors
                        .toMap(SourcePointCriteria::getDataSource,
                                a -> new DataPointCriteria[]{a.getDataPoint()}));
    }
}
