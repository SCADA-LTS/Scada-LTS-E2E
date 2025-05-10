package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.common.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.core.utils.ExecutorUtil;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateOneDataSourceMultiPointsSubCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateWatchListSubCommand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ConfigureAlarmNotificationTestCommand implements Command<ConfigureAlarmNotificationTestCommand> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        ExecutorUtil.execute(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), ConfigureTestException::new);
    }

    private void _execute() {

        DataSourceIdentifier dataSourceIdentifier = IdentifierObjectFactory
                .dataSourceName(DataSourceType.VIRTUAL_DATA_SOURCE);
        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.criteria(dataSourceIdentifier,
                UpdatePeriodType.SECOND, 60, false);

        Set<VirtualDataPointCriteria> dataPointCriterias = new HashSet<>();
        for(int i = 0; i < 30; i++) {
            dataPointCriterias.add(VirtualDataPointCriteria.alternate(i % 2 == 0 ?
                    IdentifierObjectFactory.dataPointAlarmBinaryTypeName():
                    IdentifierObjectFactory.dataPointStorungBinaryTypeName()));
        }

        WatchListCriteria watchListCriteria = WatchListCriteria
                .criteria(data(dataSourceCriteria, dataPointCriterias).toArray(
                        new VirtualDataSourcePointCriteria[]{}));

        CreateOneDataSourceMultiPointsSubCommand createOneDataSourceMultiPointsSubCommand = CreateOneDataSourceMultiPointsSubCommand.builder()
                .dataSource(dataSourceCriteria)
                .dataPoints(dataPointCriterias)
                .navigationPage(navigationPage)
                .build();

        createOneDataSourceMultiPointsSubCommand.execute();

        CreateWatchListSubCommand createWatchListSubCommand = CreateWatchListSubCommand.builder()
                .watchListCriteria(watchListCriteria)
                .navigationPage(navigationPage)
                .build();

        createWatchListSubCommand.execute();

        try(CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {
            criteriaRegister.register(UpdateDataSourceCriteria.class, dataSourceCriteria);
            criteriaRegister.register(VirtualDataPointCriteria.class, dataPointCriterias);
            criteriaRegister.register(WatchListCriteria.class, watchListCriteria);
        }
    }

    @Override
    public Class<ConfigureAlarmNotificationTestCommand> getClassTest() {
        return ConfigureAlarmNotificationTestCommand.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    private List<VirtualDataSourcePointCriteria> data(UpdateDataSourceCriteria dataSourceCriteria, Set<VirtualDataPointCriteria> dataPointCriterias) {
        List<VirtualDataSourcePointCriteria> result = new ArrayList<>();
        for(VirtualDataPointCriteria dataPointCriteria: dataPointCriterias) {
            result.add(VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointCriteria));
        }
        return result;
    }
}
