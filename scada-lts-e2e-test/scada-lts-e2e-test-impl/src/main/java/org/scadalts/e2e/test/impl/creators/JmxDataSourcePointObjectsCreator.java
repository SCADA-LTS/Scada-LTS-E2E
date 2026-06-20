package org.scadalts.e2e.test.impl.creators;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.util.Map;
import java.util.stream.Collectors;


@Log4j2
public class JmxDataSourcePointObjectsCreator extends DataSourcePointObjectsCreator<JmxDataSourceCriteria, JmxDataPointCriteria> {

    public JmxDataSourcePointObjectsCreator(NavigationPage navigationPage, JmxDataSourceCriteria dataSource,
                                            JmxDataPointCriteria... dataPoints) {
        super(navigationPage, Map.of(dataSource, new JmxDataPointObjectsCreator(navigationPage, dataSource, dataPoints)));
    }

    public JmxDataSourcePointObjectsCreator(NavigationPage navigationPage,
                                            DataSourcePointCriteria<JmxDataSourceCriteria, JmxDataPointCriteria>... dataSourcePoints) {
        super(navigationPage, CriteriaUtil.createCriteriaStructure(dataSourcePoints).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new JmxDataPointObjectsCreator(navigationPage, entry.getKey(), entry.getValue().toArray(JmxDataPointCriteria[]::new)))));
    }

    public JmxDataSourcePointObjectsCreator(NavigationPage navigationPage,
                                            JmxDataSourcePointCriteria... dataSourcePoints) {
        super(navigationPage, CriteriaUtil.createCriteriaStructure(dataSourcePoints).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new JmxDataPointObjectsCreator(navigationPage, entry.getKey(), entry.getValue().toArray(JmxDataPointCriteria[]::new)))));
    }


    public EditDataSourceWithPointListPage createDataSource(DataSourcesPage page, JmxDataSourceCriteria criteria) {

        logger.info("creating object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                criteria.getIdentifier().getType(), criteria.getXid().getValue(), criteria.getClass().getSimpleName());

        EditDataSourceWithPointListPage resultPage = page.openDataSourceCreator(criteria.getIdentifier().getType())
                .selectUpdatePeriodType(criteria.getUpdatePeriodType())
                .setUpdatePeriods(criteria.getUpdatePeriodValue())
                .setName(criteria.getIdentifier())
                .setXid(criteria.getXid())
                .setRemoteServerAddr(criteria.getRemoteServerAddress())
                .setQuantize(criteria.isQuantize())
                .setUseLocalServer(criteria.isUseLocalServer())
                .save()
                .waitOnPage(500)
                .enable(criteria.isEnabled());

        logger.info("created object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                criteria.getIdentifier().getType(), criteria.getXid().getValue(), criteria.getClass().getSimpleName());

        return resultPage;
    }


}
