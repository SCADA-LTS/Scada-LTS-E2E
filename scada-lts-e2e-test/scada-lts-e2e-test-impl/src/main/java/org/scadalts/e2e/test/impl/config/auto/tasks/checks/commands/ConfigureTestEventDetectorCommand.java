package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateEventDetectorSubCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateEventHandlerSubCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateOneDataSourceTwoPointsSubCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateScriptSubCommand;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;

import static org.scadalts.e2e.common.utils.ExecutorUtil.executeBiFunction;

@Data
public class ConfigureTestEventDetectorCommand implements Command<EventDetectorCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        executeBiFunction(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), RuntimeException::new);
    }

    private void _execute() {
        Xid dataPointToChangeXid = new Xid(TestImplConfiguration.dataPointToChangeXid);
        Xid dataPointToReadXid = new Xid(TestImplConfiguration.dataPointToReadXid);

        DataPointCriteria dataPointToChangeCriteria = DataPointCriteria.numericNoChange(dataPointToChangeXid,
                new DataPointIdentifier("datapoint_to_change"), 123);

        DataPointCriteria dataPointToReadCriteria = DataPointCriteria.numericNoChange(dataPointToReadXid,
                new DataPointIdentifier("datapoint_to_read"), 12345);

        DataSourceIdentifier eventDetectorIdentifier = new DataSourceIdentifier("datasource_event_detector_test");
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond(eventDetectorIdentifier);


        CreateOneDataSourceTwoPointsSubCommand createOneDataSourceTwoPointsSubCommand = CreateOneDataSourceTwoPointsSubCommand.builder()
                .dataPoint1(dataPointToChangeCriteria)
                .dataPoint2(dataPointToReadCriteria)
                .dataSourceCriteria(dataSourceCriteria)
                .navigationPage(navigationPage)
                .build();

        createOneDataSourceTwoPointsSubCommand.execute();

        CreateEventDetectorSubCommand createEventDetectorSubCommand = CreateEventDetectorSubCommand.builder()
                .dataPointCriteria(dataPointToChangeCriteria)
                .dataSourceCriteria(dataSourceCriteria)
                .navigationPage(navigationPage)
                .build();

        EventDetectorCriteria eventDetectorCriteria = createEventDetectorSubCommand.execute();

        CreateScriptSubCommand createScriptSubCommand = CreateScriptSubCommand.builder()
                .dataPointCriteria(dataPointToChangeCriteria)
                .navigationPage(navigationPage)
                .build();

        ScriptCriteria scriptCriteria = createScriptSubCommand.execute();

        CreateEventHandlerSubCommand createEventHandlerSubCommand = CreateEventHandlerSubCommand.builder()
                .navigationPage(navigationPage)
                .eventDetectorCriteria(eventDetectorCriteria)
                .scriptCriteria(scriptCriteria)
                .build();

        EventHandlerCriteria eventHandler = createEventHandlerSubCommand.execute();

        try(CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {
            criteriaRegister.register(DataSourceCriteria.class, dataSourceCriteria);
            criteriaRegister.register(DataSourcePointCriteria.class, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointToChangeCriteria));
            criteriaRegister.register(DataSourcePointCriteria.class, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointToReadCriteria));
            criteriaRegister.register(DataPointCriteria.class, dataPointToChangeCriteria);
            criteriaRegister.register(DataPointCriteria.class, dataPointToReadCriteria);

            criteriaRegister.register(EventHandlerCriteria.class, eventHandler);
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
