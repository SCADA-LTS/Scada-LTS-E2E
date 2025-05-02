package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.common.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.core.utils.ExecutorUtil;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
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
import org.scadalts.e2e.test.impl.creators.*;
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
        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.criteriaSecond(new DataSourceIdentifier(TestImplConfiguration.dataSourceName, DataSourceType.VIRTUAL_DATA_SOURCE));
        VirtualDataPointCriteria dataPointCriteria = VirtualDataPointCriteria.noChange(new DataPointIdentifier(TestImplConfiguration.dataPointName, DataPointType.NUMERIC));
        VirtualDataSourcePointCriteria dataSourcePointCriteria = VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointCriteria);
        WatchListCriteria watchListCriteria = WatchListCriteria.criteria(new WatchListIdentifier(TestImplConfiguration.watchListName), dataSourcePointCriteria.getIdentifier());

        try (CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {

            criteriaRegister.register(UpdateDataSourceCriteria.class, dataSourceCriteria);
            criteriaRegister.register(VirtualDataPointCriteria.class, dataPointCriteria);
            criteriaRegister.register(VirtualDataSourcePointCriteria.class, dataSourcePointCriteria);
            criteriaRegister.register(WatchListCriteria.class, watchListCriteria);

        }

        DataSourcePointObjectsCreator dataSourcePointObjectsCreator = new VirtualDataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcePointObjectsCreator.createObjects();

        DataPointObjectsCreator dataPointObjectsCreator = new VirtualDataPointObjectsCreator(navigationPage, dataSourcePointCriteria);
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
