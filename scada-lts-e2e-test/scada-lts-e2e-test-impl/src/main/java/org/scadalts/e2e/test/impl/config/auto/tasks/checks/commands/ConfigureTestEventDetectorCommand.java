package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.common.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.utils.ExecutorUtil;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateEventDetectorSubCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateEventHandlerSubCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateOneDataSourceTwoPointsSubCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateScriptRewritingValueFromToPointSubCommand;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;

@Data
public class ConfigureTestEventDetectorCommand implements Command<EventDetectorCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        ExecutorUtil.execute(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), ConfigureTestException::new);
    }

    private void _execute() {
        Xid dataPointToChangeXid = new Xid(TestImplConfiguration.dataPointToChangeXid);
        Xid dataPointToReadXid = new Xid(TestImplConfiguration.dataPointToReadXid);

        DataPointCriteria dataPointToChangeCriteria = DataPointCriteria.noChange(dataPointToChangeXid,
                new DataPointIdentifier("datapoint_to_change", DataPointType.NUMERIC), "123");

        DataPointCriteria dataPointToReadCriteria = DataPointCriteria.noChange(dataPointToReadXid,
                new DataPointIdentifier("datapoint_to_read", DataPointType.NUMERIC), "12345");

        DataSourceIdentifier eventDetectorIdentifier = new DataSourceIdentifier(TestImplConfiguration.dataSourceNameEventDetectorTest,
                DataSourceType.VIRTUAL_DATA_SOURCE);
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.criteriaSecond(eventDetectorIdentifier);

        CreateOneDataSourceTwoPointsSubCommand createOneDataSourceTwoPointsSubCommand = CreateOneDataSourceTwoPointsSubCommand.builder()
                .dataPoint1(dataPointToChangeCriteria)
                .dataPoint2(dataPointToReadCriteria)
                .dataSource(dataSourceCriteria)
                .navigationPage(navigationPage)
                .build();

        createOneDataSourceTwoPointsSubCommand.execute();

        CreateEventDetectorSubCommand createEventDetectorSubCommand = CreateEventDetectorSubCommand.builder()
                .dataPointCriteria(dataPointToChangeCriteria)
                .dataSourceCriteria(dataSourceCriteria)
                .navigationPage(navigationPage)
                .build();

        EventDetectorCriteria eventDetectorCriteria = createEventDetectorSubCommand.execute();

        CreateScriptRewritingValueFromToPointSubCommand createScriptRewritingValueFromToPointSubCommand = CreateScriptRewritingValueFromToPointSubCommand.builder()
                .dataPointFromCriteria(dataPointToChangeCriteria)
                .dataPointToCriteria(dataPointToReadCriteria)
                .navigationPage(navigationPage)
                .build();

        ScriptCriteria scriptCriteria = createScriptRewritingValueFromToPointSubCommand.execute();

        CreateEventHandlerSubCommand createEventHandlerSubCommand = CreateEventHandlerSubCommand.builder()
                .navigationPage(navigationPage)
                .eventDetectorCriteria(eventDetectorCriteria)
                .scriptCriteria(scriptCriteria)
                .build();

        EventHandlerCriteria eventHandlerCriteria = createEventHandlerSubCommand.execute();

        try(CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {
            criteriaRegister.register(DataSourceCriteria.class, dataSourceCriteria);
            criteriaRegister.register(DataSourcePointCriteria.class, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointToChangeCriteria));
            criteriaRegister.register(DataSourcePointCriteria.class, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointToReadCriteria));
            criteriaRegister.register(DataPointCriteria.class, dataPointToChangeCriteria);
            criteriaRegister.register(DataPointCriteria.class, dataPointToReadCriteria);

            criteriaRegister.register(EventHandlerCriteria.class, eventHandlerCriteria);
            criteriaRegister.register(EventDetectorCriteria.class, eventDetectorCriteria);
            criteriaRegister.register(ScriptCriteria.class, scriptCriteria);
        }
    }

    @Override
    public Class<EventDetectorCheckTest> getClassTest() {
        return EventDetectorCheckTest.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
