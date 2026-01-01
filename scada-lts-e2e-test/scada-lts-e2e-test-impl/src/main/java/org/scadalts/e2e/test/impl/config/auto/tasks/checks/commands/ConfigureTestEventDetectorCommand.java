package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.core.utils.ExecutorUtil;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
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
@Log4j2
public class ConfigureTestEventDetectorCommand implements Command<EventDetectorCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        try {
            ExecutorUtil.execute(this::_execute,
                    CriteriaRegisterAggregator.INSTANCE::removeRegister,
                    getClassTest(), ConfigureTestException::new);
        } catch (Throwable th) {
            logger.warn(th.getMessage(), th);
            throw th;
        }
    }

    private void _execute() {
        Xid dataPointToChangeXid = new Xid(TestImplConfiguration.dataPointToChangeXid);
        Xid dataPointToReadXid = new Xid(TestImplConfiguration.dataPointToReadXid);

        VirtualDataPointCriteria dataPointToChangeCriteria = VirtualDataPointCriteria.noChange(dataPointToChangeXid,
                new DataPointIdentifier("dp_to_change", DataPointType.NUMERIC), "123");

        VirtualDataPointCriteria dataPointToReadCriteria = VirtualDataPointCriteria.noChange(dataPointToReadXid,
                new DataPointIdentifier("dp_to_read", DataPointType.NUMERIC), "12345");

        DataSourceIdentifier dataSourceIdentifier = new DataSourceIdentifier(TestImplConfiguration.dataSourceNameEventDetectorTest,
                DataSourceType.VIRTUAL_DATA_SOURCE);
        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.criteriaSecond(dataSourceIdentifier);

        CreateOneDataSourceTwoPointsSubCommand createOneDataSourceTwoPointsSubCommand = CreateOneDataSourceTwoPointsSubCommand.builder()
                .dataPoint1(dataPointToChangeCriteria)
                .dataPoint2(dataPointToReadCriteria)
                .dataSource(dataSourceCriteria)
                .navigationPage(navigationPage)
                .build();

        createOneDataSourceTwoPointsSubCommand.execute();

        EventDetectorIdentifier eventDetectorIdentifier =
                new EventDetectorIdentifier("ed_event_detector_test",EventDetectorType.CHANGE);

        CreateEventDetectorSubCommand createEventDetectorSubCommand = CreateEventDetectorSubCommand.builder()
                .dataPointCriteria(dataPointToChangeCriteria)
                .dataSourceCriteria(dataSourceCriteria)
                .eventDetectorIdentifier(eventDetectorIdentifier)
                .alarmLevel(AlarmLevel.NONE)
                .navigationPage(navigationPage)
                .build();

        EventDetectorCriteria eventDetectorCriteria = createEventDetectorSubCommand.execute();

        CreateScriptRewritingValueFromToPointSubCommand createScriptRewritingValueFromToPointSubCommand = CreateScriptRewritingValueFromToPointSubCommand.builder()
                .dataPointFromCriteria(VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointToChangeCriteria))
                .dataPointToCriteria(VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointToReadCriteria))
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
            criteriaRegister.register(UpdateDataSourceCriteria.class, dataSourceCriteria);
            criteriaRegister.register(VirtualDataSourcePointCriteria.class, VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointToChangeCriteria));
            criteriaRegister.register(VirtualDataSourcePointCriteria.class, VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointToReadCriteria));
            criteriaRegister.register(VirtualDataPointCriteria.class, dataPointToChangeCriteria);
            criteriaRegister.register(VirtualDataPointCriteria.class, dataPointToReadCriteria);

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
