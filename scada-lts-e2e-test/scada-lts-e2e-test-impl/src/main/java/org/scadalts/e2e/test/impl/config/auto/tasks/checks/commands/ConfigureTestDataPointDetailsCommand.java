package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.common.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.core.utils.ExecutorUtil;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.WatchListIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.creators.DataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.tests.check.datapoint.DataPointDetailsCheckTestsSuite;

@Data
public class ConfigureTestDataPointDetailsCommand implements Command<DataPointDetailsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        ExecutorUtil.execute(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), ConfigureTestException::new);

    }

    private void _execute() {
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.criteriaSecond(new DataSourceIdentifier(TestImplConfiguration.dataSourceName, DataSourceType.VIRTUAL_DATA_SOURCE));
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(new DataPointIdentifier(TestImplConfiguration.dataPointName, DataPointType.NUMERIC));
        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);
        WatchListCriteria watchListCriteria = WatchListCriteria.criteria(new WatchListIdentifier(TestImplConfiguration.watchListName), dataSourcePointCriteria.getIdentifier());

        try (CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {

            criteriaRegister.register(DataSourceCriteria.class, dataSourceCriteria);
            criteriaRegister.register(DataPointCriteria.class, dataPointCriteria);
            criteriaRegister.register(DataSourcePointCriteria.class, dataSourcePointCriteria);
            criteriaRegister.register(WatchListCriteria.class, watchListCriteria);

        }

        DataSourcePointObjectsCreator dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcePointObjectsCreator.createObjects();

        DataPointObjectsCreator dataPointObjectsCreator = new DataPointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataPointObjectsCreator.createObjects();

        WatchListObjectsCreator watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, watchListCriteria);
        watchListObjectsCreator.createObjects();
    }

    @Override
    public Class<DataPointDetailsCheckTestsSuite> getClassTest() {
        return DataPointDetailsCheckTestsSuite.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
