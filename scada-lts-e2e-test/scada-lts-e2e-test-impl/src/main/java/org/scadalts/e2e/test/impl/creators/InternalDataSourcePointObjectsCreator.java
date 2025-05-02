package org.scadalts.e2e.test.impl.creators;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Log4j2
public class InternalDataSourcePointObjectsCreator extends DataSourcePointObjectsCreator<UpdateDataSourceCriteria, InternalDataPointCriteria> {

    public InternalDataSourcePointObjectsCreator(NavigationPage navigationPage, UpdateDataSourceCriteria dataSource,
                                                 InternalDataPointCriteria... dataPoints) {
        super(navigationPage, Map.of(dataSource, new InternalDataPointObjectsCreator(navigationPage, dataSource, dataPoints)));
    }

    public InternalDataSourcePointObjectsCreator(NavigationPage navigationPage, InternalDataSourcePointCriteria... dataSourcePoints) {
        super(navigationPage, createMap(navigationPage, dataSourcePoints));
    }

    private static Map<UpdateDataSourceCriteria, DataPointObjectsCreator<UpdateDataSourceCriteria, InternalDataPointCriteria>> createMap(NavigationPage navigationPage, InternalDataSourcePointCriteria... dataSourcePoints) {
        return CriteriaUtil.createCriteriaStructure(dataSourcePoints).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new InternalDataPointObjectsCreator(navigationPage, entry.getKey(), entry.getValue().toArray(InternalDataPointCriteria[]::new))));
    }

    private static Map<UpdateDataSourceCriteria, DataPointObjectsCreator<UpdateDataSourceCriteria, InternalDataPointCriteria>> createMap(NavigationPage navigationPage, List<DataSourcePointCriteria<UpdateDataSourceCriteria, InternalDataPointCriteria>> dataSourcePoints) {
        return CriteriaUtil.createCriteriaStructure(dataSourcePoints).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new InternalDataPointObjectsCreator(navigationPage, entry.getKey(), entry.getValue())));
    }

    private static Map<UpdateDataSourceCriteria, DataPointObjectsCreator<UpdateDataSourceCriteria, InternalDataPointCriteria>> createMap(NavigationPage navigationPage, UpdateDataSourceCriteria dataSource, InternalDataPointCriteria... dataPoints) {
        return CriteriaUtil.createCriteriaStructure(Stream.of(dataPoints).map(a -> new DataSourcePointCriteria<>(dataSource, a)).collect(Collectors.toList())).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new InternalDataPointObjectsCreator(navigationPage, entry.getKey(), entry.getValue().toArray(InternalDataPointCriteria[]::new))));
    }

    public EditDataSourceWithPointListPage createDataSource(DataSourcesPage page, UpdateDataSourceCriteria criteria) {

        logger.info("create object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                criteria.getIdentifier().getType(), criteria.getXid().getValue(), criteria.getClass().getSimpleName());

        return page.openDataSourceCreator(criteria.getIdentifier().getType())
                .selectUpdatePeriodType(criteria.getUpdatePeriodType())
                .setUpdatePeriods(criteria.getUpdatePeriodValue())
                .setName(criteria.getIdentifier())
                .setXid(criteria.getXid())
                .save()
                .waitOnPage(500)
                .enable(criteria.isEnabled());
    }


}
