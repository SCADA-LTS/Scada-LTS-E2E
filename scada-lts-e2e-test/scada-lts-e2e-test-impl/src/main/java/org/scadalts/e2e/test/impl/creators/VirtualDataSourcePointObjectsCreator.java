package org.scadalts.e2e.test.impl.creators;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.util.Map;
import java.util.stream.Collectors;


@Log4j2
public class VirtualDataSourcePointObjectsCreator extends DataSourcePointObjectsCreator<UpdateDataSourceCriteria, VirtualDataPointCriteria> {

    public VirtualDataSourcePointObjectsCreator(NavigationPage navigationPage, UpdateDataSourceCriteria dataSource,
                                                VirtualDataPointCriteria... dataPoints) {
        super(navigationPage, Map.of(dataSource, new VirtualDataPointObjectsCreator(navigationPage, dataSource, dataPoints)));
    }

    public VirtualDataSourcePointObjectsCreator(NavigationPage navigationPage,
                                                DataSourcePointCriteria<UpdateDataSourceCriteria, VirtualDataPointCriteria>... dataSourcePoints) {
        super(navigationPage, CriteriaUtil.createCriteriaStructure(dataSourcePoints).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new VirtualDataPointObjectsCreator(navigationPage, entry.getKey(), entry.getValue().toArray(VirtualDataPointCriteria[]::new)))));
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
